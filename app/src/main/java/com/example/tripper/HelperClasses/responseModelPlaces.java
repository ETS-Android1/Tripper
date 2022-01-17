package com.example.tripper.HelperClasses;

public class responseModelPlaces {
    String placeId, Title, Description,locationId, placeImage ;

    public responseModelPlaces() {
    }

    public responseModelPlaces(String placeId, String title, String description, String locationId,
                               String placeImage) {
        this.placeId = placeId;
        Title = title;
        Description = description;
        this.locationId = locationId;
        this.placeImage = placeImage;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(String placeImage) {
        this.placeImage = placeImage;
    }
}
