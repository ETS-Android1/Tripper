package com.example.tripper.LocationContributor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.tripper.R;

public class AddPlaces extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);

        String[] Categories = new String[] {"Beaches", "Hill Station", "Island", "Town & Cities"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,R.layout.dropdown_item,Categories);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.autoCompleteTextView);
        editTextFilledExposedDropdown.setAdapter(adapter);
    }
}