package com.davidulloa.examen.ui.file;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.davidulloa.examen.R;
import com.davidulloa.examen.binding.FragmentDataBindingComponent;
import com.davidulloa.examen.data.local.models.Image;
import com.davidulloa.examen.databinding.FragmentDashboardBinding;
import com.davidulloa.examen.databinding.FragmentNotificationBinding;
import com.davidulloa.examen.di.Injectable;
import com.davidulloa.examen.ui.adapters.ImageRecyclerAdpater;
import com.davidulloa.examen.ui.adapters.MovieRecyclerAdapter;
import com.davidulloa.examen.ui.common.NavigationController;
import com.davidulloa.examen.ui.maps.MapsFragment;
import com.davidulloa.examen.ui.viewmodel.ImageViewModel;
import com.davidulloa.examen.util.AutoClearedValue;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilesFragment extends Fragment implements Injectable, View.OnClickListener, PermissionListener{


    @Inject
    ViewModelProvider.Factory viewModelProvider;

    @Inject
    NavigationController navigationController;

    androidx.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<FragmentNotificationBinding> binding;

    AutoClearedValue<ImageRecyclerAdpater> adapter;

    private ImageViewModel imageViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private StorageReference mStorageRef;
    private PermissionListener allPermissionsListener;
    private static final int PICK_IMAGE = 2;
    private static final int MULTI = 3;
    private List<Image> images = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilesFragment newInstance(String param1, String param2) {
        FilesFragment fragment = new FilesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageViewModel = ViewModelProviders.of(getActivity()).get(ImageViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNotificationBinding fragmentNotificationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification
                ,container,false,dataBindingComponent);

        ImageRecyclerAdpater adpater = new ImageRecyclerAdpater(dataBindingComponent);
        binding = new AutoClearedValue<>(this, fragmentNotificationBinding);

        binding.get().recyclerViewImagenes.setAdapter(adpater);
        adapter = new AutoClearedValue<>(this,adpater);
        binding.get().fabAddIncidence.setOnClickListener(this);


        return fragmentNotificationBinding.getRoot();
    }

    private void checkPermissions() {
        PermissionListener dialogOnDeniedPermissionListener =
                DialogOnDeniedPermissionListener.Builder.withContext(getContext())
                        .withTitle("Permisos")
                        .withMessage("Los permisos solicitados son necesarios para poder seleccionar una foto de perfil")
                        .withButtonText("Aceptar")
                        .withIcon(R.mipmap.ic_launcher)
                        .build();

        allPermissionsListener = new CompositePermissionListener(
                (PermissionListener) this,
                dialogOnDeniedPermissionListener
        );

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(allPermissionsListener)
                .check();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ClipData clipData = data.getClipData();
        if (requestCode == PICK_IMAGE && resultCode == getActivity().RESULT_OK){
            if(clipData == null) {
                Uri imagenSeleccionada = data.getData(); // content://gallery/photos/..
               images.add(new Image(imagenSeleccionada));
                }else{
                for(int i=0; i< clipData.getItemCount(); i++){
                    images.add(new Image(clipData.getItemAt(i).getUri()));

                }

                List<Image> imagest = new ArrayList<>();

                imageViewModel.saveImage(images).observe(getViewLifecycleOwner(),images1 -> {
                    if(images1 != null){
                        imagest.add(images1);
                        adapter.get().replace(imagest);
                    } else {
                        adapter.get().replace(Collections.emptyList());
                    }
                });


            }
        }
    }


    private void loadPicture(){
        Intent getIntent = new Intent();
        getIntent.setType("image/*");
        getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        getIntent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(getIntent,"Selecciona las imagenes"),PICK_IMAGE);
    }

    @Override
    public void onClick(View view) {
        checkPermissions();
        loadPicture();

    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {

    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {

    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

    }
}