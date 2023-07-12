package com.sapai_4.ai.model;

public class Favourites {
    private int id;
    private String title;
    private String descrip;
    private byte[] image;

    public Favourites(int id, String title, String descrip, byte[] image) {
        this.id = id;
        this.title = title;
        this.descrip = descrip;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
