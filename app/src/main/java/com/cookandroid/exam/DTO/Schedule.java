package com.cookandroid.exam.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("context")
    @Expose
    private String context;
    @SerializedName("endHms")
    @Expose
    private String endHms;
    @SerializedName("endYmd")
    @Expose
    private String endYmd;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("startHms")
    @Expose
    private String startHms;
    @SerializedName("startYmd")
    @Expose
    private String startYmd;
    @SerializedName("title")
    @Expose
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getEndHms() { return endHms; }

    public void setEndHms(String endHms) { this.endHms = endHms; }

    public String getEndYmd() { return endYmd; }

    public void setEndYmd(String endYmd) { this.endYmd = endYmd; }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartHms() { return startHms; }

    public void setStartHms(String startHms) { this.startHms = startHms; }

    public String getStartYmd() { return startYmd; }

    public void setStartYmd(String startYmd) { this.startYmd = startYmd; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Schedule(String color, String context, String endHms, String endYmd, String location, String startHms, String startYmd, String title){
        this.color = color;
        this.context = context;
        this.endHms = endHms;
        this.endYmd = endYmd;
        this.location = location;
        this.startYmd = startYmd;
        this.startHms = startHms;
        this.endYmd = endYmd;
        this.title = title;
    }
}

