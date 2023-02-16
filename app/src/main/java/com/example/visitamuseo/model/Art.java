package com.example.visitamuseo.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class Art implements SliderViewInterface {
    @PrimaryKey
    @NotNull
    private String name;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "urlImageArt")
    private String urlImage;
    private String nameExhibition;

    public Art(String name, String author, String description, String urlImage) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.urlImage = urlImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameExhibition() {
        return nameExhibition;
    }

    public void setNameExhibition(String nameExhibition) {
        this.nameExhibition = nameExhibition;
    }

    @Override
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return "Art{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", nameExhibition='" + nameExhibition + '\'' +
                '}';
    }
}
