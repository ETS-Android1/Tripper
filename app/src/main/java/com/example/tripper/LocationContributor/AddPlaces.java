package com.example.tripper.LocationContributor;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.tripper.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class AddPlaces extends AppCompatActivity {

    private CircleImageView Imagepicker;
    private ArrayList<Uri> imageUris;

    private static final int PICK_IMAGES_CODES = 0;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);

        Imagepicker = findViewById(R.id.image_selection);
        imageUris = new ArrayList<>();

        String[] Categories = new String[]{"Beaches", "Hill Station", "Island", "Town & Cities"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, R.layout.dropdown_item, Categories);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.autoCompleteTextView);
        editTextFilledExposedDropdown.setAdapter(adapter);

        Imagepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                // setting type to select to be image
                intent.setType("image/*");

                // allowing multiple image to be selected
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES_CODES);
            }
        });

    }


    public void callBackScreen(View view) {
        AddPlaces.super.onBackPressed();
    }

    public void addPlace(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked
        if (requestCode == PICK_IMAGES_CODES && resultCode == RESULT_OK && null != data) {
            // Get the Image from data
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    // adding imageuri in array
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    imageUris.add(imageurl);
                }
                // setting 1st selected image into image switcher
                Imagepicker.setImageURI(imageUris.get(0));
                position = 0;
            } else {
                Uri imageurl = data.getData();
                imageUris.add(imageurl);
                Imagepicker.setImageURI(imageUris.get(0));
                position = 0;
            }
        } else {
            // show this if no image is selected
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}