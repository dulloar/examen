package com.davidulloa.examen.data.remote.repository;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.davidulloa.examen.data.local.models.Image;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RepositryImage {
   /* FirebaseStorage storageRef;
    StorageReference storageReference;

    public RepositryImage() {
        this.storageRef = FirebaseStorage.getInstance();
        this.storageReference = storageRef.getReference();
        this.storageReference.child("images");
    }

    public LiveData<List<Image>> saveImage(List<Image> images){
        MutableLiveData<List<Image>> mImage = new MutableLiveData<>();

        for(Image image:images){

            image.setPath(uploadImages(image.getPath()));
        }
        mImage.setValue(images);
        return mImage;
    }



    private String uploadImages(String image){
        String path;
        Uri file = Uri.fromFile(new File(image));
        StorageReference riversRef = storageReference.child("images/"+file.getLastPathSegment());

        UploadTask uploadTask = riversRef.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

       return riversRef.getDownloadUrl().toString();
    }*/
}
