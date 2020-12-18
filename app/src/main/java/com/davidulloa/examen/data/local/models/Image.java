package com.davidulloa.examen.data.local.models;

import android.net.Uri;

public class Image {
    private String id;
    private Uri uri;

    public Image(Uri uri) {
        this.uri = uri;
    }

    public Image() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
