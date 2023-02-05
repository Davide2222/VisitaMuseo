package com.example.visitamuseo.model;

public class Art implements SliderViewInterface {
    private String name;
    private String author;
    private String description;
    private String urlImage;

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

    @Override
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
