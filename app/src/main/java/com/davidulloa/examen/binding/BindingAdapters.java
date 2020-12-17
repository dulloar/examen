package com.davidulloa.examen.binding;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.davidulloa.examen.data.remote.ApiConstants;

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showOrHide(View view, boolean show){
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("urlImageP")
    public static void setImage(ImageView imageView, String path){
        Glide.with(imageView.getContext())
                .load(ApiConstants.IMAGE_API+path).into(imageView);
    }

}
