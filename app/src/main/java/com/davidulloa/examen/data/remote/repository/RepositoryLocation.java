package com.davidulloa.examen.data.remote.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.davidulloa.examen.data.local.models.UserLocation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RepositoryLocation {
   /* private DatabaseReference mDatabase;

    public RepositoryLocation() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void saveLocations(UserLocation userLocation){
        MutableLiveData<List<UserLocation>> mLocations;
        mDatabase.child("location").setValue(userLocation);
    }*/
}
