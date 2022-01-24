package com.example.tripper.HelperClasses.HomeAdapter;

public class FeaturedHelperClass {
    String placeImage;
    String Title;
    String Description;

    public FeaturedHelperClass() {
    }

    public FeaturedHelperClass(String placeImage, String title, String description) {
        this.placeImage = placeImage;
        Title = title;
        Description = description;
    }

    public String getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(String placeImage) {
        this.placeImage = placeImage;
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
}
