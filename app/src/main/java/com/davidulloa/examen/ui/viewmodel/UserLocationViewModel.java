package com.davidulloa.examen.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.davidulloa.examen.data.local.models.UserLocation;
import com.davidulloa.examen.data.remote.repository.RepositoryLocation;

public class UserLocationViewModel extends ViewModel {
    private final RepositoryLocation repositoryLocation;

    public UserLocationViewModel() {
        this.repositoryLocation = new RepositoryLocation();
    }

    public LiveData<UserLocation> getuserLocation(){
        return this.repositoryLocation.getLocation();
    }

    public void saveLocation(UserLocation userLocation){
        this.repositoryLocation.saveLocations(userLocation);
    }
}
