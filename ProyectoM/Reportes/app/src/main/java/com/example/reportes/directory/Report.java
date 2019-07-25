package com.example.reportes.directory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Report {



    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("fechita")
    @Expose
    private String fechita;

    @SerializedName("user_id")
    @Expose
    private int user_id;


    public Report() {
        super();
    }

    public Report(String title, String description, String image, String fechita, int user_id) {
        super();
        this.title = title;
        this.description = description;
        this.image = image;
        this.fechita = fechita;
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFechita() {
        return fechita;
    }

    public void setFechita(String fechita) {
        this.fechita = fechita;
    }

}