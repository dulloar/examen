package com.davidulloa.examen.data.local.models;

public class Image {
    private String id;
    private String path;

    public Image(String path) {
        this.path = path;
    }

    public Image() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
