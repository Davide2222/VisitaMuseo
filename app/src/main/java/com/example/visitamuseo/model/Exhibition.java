package com.example.visitamuseo.model;

import java.io.Serializable;

public class Exhibition implements SliderViewInterface,Serializable {
    private String name;
    private String description;
    private String urlImage;

    public Exhibition(String name, String description, String urlImage) {
        this.name = name;
        this.description = description;
        this.urlImage = urlImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getUrlImage() {return urlImage;}

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
