package com.davidulloa.examen.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.davidulloa.examen.data.local.models.Image;
import com.davidulloa.examen.data.remote.repository.RepositryImage;

import java.util.List;

import javax.inject.Inject;

public class ImageViewModel extends ViewModel {
    private final RepositryImage repositryImage;

    public ImageViewModel() {
        this.repositryImage = new RepositryImage();
    }

    public LiveData<Image> saveImage(List<Image> images){
        return this.repositryImage.saveImage(images);
    }
}
