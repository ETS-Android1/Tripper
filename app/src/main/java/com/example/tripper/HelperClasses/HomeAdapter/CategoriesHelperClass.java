package com.example.tripper.HelperClasses.HomeAdapter;

import android.graphics.drawable.GradientDrawable;

public class CategoriesHelperClass {
    GradientDrawable gradientDrawable;
    int image;
    String title;

    public CategoriesHelperClass(GradientDrawable gradientDrawable, int image, String title) {
        this.gradientDrawable = gradientDrawable;
        this.image = image;
        this.title = title;
    }

    public GradientDrawable getGradientDrawable() {
        return gradientDrawable;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setGradientDrawable(GradientDrawable gradientDrawable) {
        this.gradientDrawable = gradientDrawable;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
