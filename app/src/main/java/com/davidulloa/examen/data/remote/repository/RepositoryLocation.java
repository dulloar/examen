package com.davidulloa.examen.data.remote.repository;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.davidulloa.examen.data.local.models.UserLocation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RepositoryLocation {
    private String LOCATION = "location";
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private MutableLiveData<UserLocation> mUserLocation = new MutableLiveData<>();

    public RepositoryLocation() {
        this.database = FirebaseDatabase.getInstance("https://imagenes-43960-default-rtdb.firebaseio.com/");
        this.reference = database.getReference(LOCATION);
    }

    public void saveLocations(UserLocation userLocation){
       this.reference.push().setValue(userLocation);
    }

    public LiveData<UserLocation> getLocation(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserLocation userLocation= dataSnapshot.getValue(UserLocation.class);
                setUserLocation(userLocation);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserLocation userLocation= dataSnapshot.getValue(UserLocation.class);
                setUserLocation(userLocation);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return mUserLocation;
    }

    private void setUserLocation(UserLocation userLocation) {
        mUserLocation.setValue(userLocation);
    }
}
