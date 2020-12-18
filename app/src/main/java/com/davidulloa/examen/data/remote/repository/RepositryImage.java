package com.davidulloa.examen.data.remote.repository;

import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.davidulloa.examen.R;
import com.davidulloa.examen.data.local.models.Image;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RepositryImage {

    private static final String MY_PHOTO = "my_photo";

    private static final String PATH_PROFILE = "profile";
    private static final String PATH_PHOTO_URL = "photoUrl";
    FirebaseStorage storageRef;
    StorageReference storageReference;
    MutableLiveData<Image> mImage = new MutableLiveData<>();

    public RepositryImage() {
        this.storageRef = FirebaseStorage.getInstance();
        this.storageReference = storageRef.getReference();
        this.storageReference.child("images");
        configImage();
    }

    public LiveData<Image> saveImage(List<Image> images){


        for(Image image:images){

            uploadImages(image.getUri());
        }
        return mImage;
    }



    private void uploadImages(Uri image){
        StorageReference profileReference = storageReference.child(PATH_PROFILE);

        StorageReference photoReference = profileReference.child(UUID.randomUUID().toString()+".jpg");

        photoReference.putFile(image)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                setUri(uri);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });

    }

    private void configImage(){
        storageReference.child(PATH_PROFILE).child(MY_PHOTO).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL);

                        setUri(uri);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void setUri(Uri uri) {
        mImage.setValue(new Image(uri));
    }
}
