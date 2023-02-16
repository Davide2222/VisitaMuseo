package com.example.visitamuseo.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity
public class Exhibition implements SliderViewInterface,Serializable {
    @PrimaryKey @NotNull
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "urlImageExhibitions")
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
