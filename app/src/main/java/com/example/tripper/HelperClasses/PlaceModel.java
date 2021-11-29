package com.example.tripper.HelperClasses;

import java.util.ArrayList;

public class PlaceModel {
    public String placeId;
    public String placeDescription;
    public String placeBudget;
    public String userId;
    public String placeTitle;
    public String placeVisitTime;
    public String placeAddress;
    public String placeCategory;

    public PlaceModel(String placeId, String placeDescription, String placeBudget, String userId, String placeTitle, String placeVisitTime, String placeAddress, String placeCategory, ArrayList<String> imageUrls) {
        this.placeId = placeId;
        this.placeDescription = placeDescription;
        this.placeBudget = placeBudget;
        this.userId = userId;
        this.placeTitle = placeTitle;
        this.placeVisitTime = placeVisitTime;
        this.placeAddress = placeAddress;
        this.placeCategory = placeCategory;
        this.imageUrls = imageUrls;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public ArrayList<String> imageUrls;


    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getPlaceBudget() {
        return placeBudget;
    }

    public void setPlaceBudget(String placeBudget) {
        this.placeBudget = placeBudget;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PlaceModel() {
    }


    public String getPlaceCategory() {
        return placeCategory;
    }

    public void setPlaceCategory(String placeCategory) {
        this.placeCategory = placeCategory;
    }


    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public String getPlaceVisitTime() {
        return placeVisitTime;
    }

    public void setPlaceVisitTime(String placeVisitTime) {
        this.placeVisitTime = placeVisitTime;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }


}
