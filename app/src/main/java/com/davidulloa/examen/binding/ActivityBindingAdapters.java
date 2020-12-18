package com.davidulloa.examen.binding;

import android.app.Fragment;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.davidulloa.examen.data.remote.ApiConstants;

import javax.inject.Inject;

public class ActivityBindingAdapters {
    final AppCompatActivity activity;

    @Inject
    public ActivityBindingAdapters(AppCompatActivity activity){
        this.activity = activity;
    }


}
