package com.davidulloa.examen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.davidulloa.examen.databinding.ActivityMainBinding;
import com.davidulloa.examen.di.Injectable;
import com.davidulloa.examen.ui.common.NavigationController;
import com.davidulloa.examen.ui.dashboard.DashboardFragment;
import com.davidulloa.examen.ui.file.FilesFragment;
import com.davidulloa.examen.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector, Injectable, MultiplePermissionsListener {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    NavigationController navigationController;

    private ActivityMainBinding mainBinding;
    private MultiplePermissionsListener allPermissionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        if(mainBinding == null){
            mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        }
        setSupportActionBar(mainBinding.toolbar.toolbar);
        mainBinding.toolbar.tvTittleScreen.setText("Inicio");
        mainBinding.navView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    Fragment selectedFragment = null;
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_dashboard:
                                mainBinding.toolbar.tvTittleScreen.setText("Ubicación");
                                selectedFragment = new DashboardFragment();
                                break;
                            case R.id.navigation_notifications:
                                mainBinding.toolbar.tvTittleScreen.setText("Imagenes");
                                selectedFragment = new FilesFragment();
                                break;
                            default:
                                mainBinding.toolbar.tvTittleScreen.setText("Inicio");
                                selectedFragment = new HomeFragment();
                                break;

                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                selectedFragment).commit();
                        return true;
                    }
                });
    }

    private void checkPermissions() {
        MultiplePermissionsListener permissionsListener =
                DialogOnAnyDeniedMultiplePermissionsListener.Builder
                        .withContext(this)
                        .withTitle("Permisos")
                        .withMessage("Los permisos solicitados son necesarios para poder accesde a la ubicacion")
                        .withButtonText("Aceptar")
                        .withIcon(R.mipmap.ic_launcher)
                        .build();

        allPermissionsListener = new CompositeMultiplePermissionsListener(
                (MultiplePermissionsListener) this,
                permissionsListener
        );

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.FOREGROUND_SERVICE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(allPermissionsListener)
                .check();
    }

    @Override
    protected void onDestroy() {
        stopLocationService();
        super.onDestroy();
    }

    private boolean isLocationServiceRunnig(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null){
            for(ActivityManager.RunningServiceInfo service:activityManager.getRunningServices(Integer.MAX_VALUE)){
                if(LocationService.class.getName().equals(service.service.getClassName())){
                    if(service.foreground){
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private void startLocationService(){
        if(!isLocationServiceRunnig()){
            Intent intent = new Intent(getApplicationContext(),LocationService.class);
            intent.setAction(Constants.ACTION_START);
            startService(intent);
        }
    }

    private void stopLocationService(){
        if(isLocationServiceRunnig()){
            Intent intent = new Intent(getApplicationContext(),LocationService.class);
            intent.setAction(Constants.ACTION_STOP);
            startService(intent);
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }



    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        startLocationService();
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

    }
}