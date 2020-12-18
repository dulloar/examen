package com.davidulloa.examen;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.davidulloa.examen.app.MyApp;
import com.davidulloa.examen.data.local.models.UserLocation;
import com.davidulloa.examen.data.remote.repository.RepositoryLocation;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service {

    private RepositoryLocation repositoryLocation;

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null && locationResult.getLastLocation() != null) {
                UserLocation userLocation = new UserLocation();
                double lat = locationResult.getLastLocation().getLatitude();
                double lon = locationResult.getLastLocation().getLongitude();
                userLocation.setLat(lat);
                userLocation.setLon(lon);
                repositoryLocation.saveLocations(userLocation);
                Log.d("LOCATION_UPDATE", lat + "," + lon);
            }
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("no hay implementacion");
    }

    private void startLocationService() {
        repositoryLocation = new RepositoryLocation();
        String chanelId = "location_notification_cahnel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), chanelId);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Servicio de localizazion activo");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText("Corriendo");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(chanelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(chanelId
                        , "Servicio de localizacion", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("Este canal es usado por el servicio de lozalizacion");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(60*30*1000);//
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        startForeground(Constants.LOCATION_SERVICE_ID,builder.build());

    }


    private void stopLocatoinService(){
        LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback);
        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String action = intent.getAction();
            if(action != null){
                if(action.equals(Constants.ACTION_START)){
                    startLocationService();
                }else if(action.equals(Constants.ACTION_STOP)){
                    stopLocatoinService();
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

}
