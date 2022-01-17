package com.example.tripper.HelperClasses;

public class responseModelPlaces {
    String placeId, Title,placeImage,name,state ;

    public responseModelPlaces() {
    }

    public responseModelPlaces(String placeId, String title, String placeImage, String name, String state) {
        this.placeId = placeId;
        Title = title;
        this.placeImage = placeImage;
        this.name = name;
        this.state = state;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(String placeImage) {
        this.placeImage = placeImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
