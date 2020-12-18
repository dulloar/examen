package com.davidulloa.examen.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;

import com.davidulloa.examen.R;
import com.davidulloa.examen.data.local.models.Image;
import com.davidulloa.examen.data.local.models.Movie;
import com.davidulloa.examen.databinding.ImageBinding;
import com.davidulloa.examen.databinding.MovieListBinding;
import com.davidulloa.examen.ui.common.DataBoundListAdapter;

public class ImageRecyclerAdpater extends DataBoundListAdapter<Image, ImageBinding> {

    private final androidx.databinding.DataBindingComponent dataBindingComponent;

    public ImageRecyclerAdpater(DataBindingComponent dataBindingComponent) {
        this.dataBindingComponent = dataBindingComponent;
    }

    @Override
    protected ImageBinding createBinding(ViewGroup parent) {

        ImageBinding imageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.image,parent,false,dataBindingComponent);

        return imageBinding;
    }

    @Override
    protected void bind(ImageBinding binding, Image item) {
        binding.setImage(item);
    }

    @Override
    protected boolean areItemsTheSame(Image oldItem, Image newItem) {
        return oldItem == newItem;
    }

    @Override
    protected boolean areContentsTheSame(Image oldItem, Image newItem) {
        return oldItem.getUri() != newItem.getUri();
    }
}
