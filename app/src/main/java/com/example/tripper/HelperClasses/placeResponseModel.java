package com.example.tripper.HelperClasses;

public class placeResponseModel {
    String title,Description,VisitTime,Budget,Address,placeImage,modifiedDate;

    public placeResponseModel() {
    }

    public placeResponseModel(String title, String description, String visitTime, String budget,
                              String address, String placeImage, String modifiedDate) {
        this.title = title;
        Description = description;
        VisitTime = visitTime;
        Budget = budget;
        Address = address;
        this.placeImage = placeImage;
        this.modifiedDate = modifiedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getVisitTime() {
        return VisitTime;
    }

    public void setVisitTime(String visitTime) {
        VisitTime = visitTime;
    }

    public String getBudget() {
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(String placeImage) {
        this.placeImage = placeImage;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
