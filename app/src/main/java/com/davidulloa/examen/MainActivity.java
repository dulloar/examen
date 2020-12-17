package com.davidulloa.examen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.davidulloa.examen.databinding.ActivityMainBinding;
import com.davidulloa.examen.di.Injectable;
import com.davidulloa.examen.ui.common.NavigationController;
import com.davidulloa.examen.ui.dashboard.DashboardFragment;
import com.davidulloa.examen.ui.file.FilesFragment;
import com.davidulloa.examen.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector, Injectable {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    NavigationController navigationController;

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
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

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}