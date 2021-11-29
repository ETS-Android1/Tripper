package com.example.tripper.LocationContributor;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.tripper.Databases.SessionManager;
import com.example.tripper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class AddPlaces extends AppCompatActivity {

    private CircleImageView Imagepicker;
    private Uri imageUris;
    private String urlStrings;
    private ProgressDialog progressDialog;
    private static final int PICK_IMAGES_CODES = 0;
    private TextInputLayout title, description, categories, bestVisitTime, budget, address;
    private String phone,timestamp;
    int position = 0;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);

        Imagepicker = findViewById(R.id.image_selection);
        title = findViewById(R.id.addplace_title);
        description = findViewById(R.id.addplace_description);
        categories = findViewById(R.id.addplace_categories);
        bestVisitTime = findViewById(R.id.addplace_bestVisitTime);
        budget = findViewById(R.id.addplace_budget);
        address = findViewById(R.id.addplace_address);

        //progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Data. Please Wait!!");

        firebaseAuth = FirebaseAuth.getInstance();


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
        inputData();
    }

    private String placeTitle, placeDescription, placeCategory, placeVisitTime, placeBudget, placeAddress;

    private void inputData() {
        placeTitle = title.getEditText().getText().toString().trim();
        placeDescription = description.getEditText().getText().toString().trim();
        placeCategory = categories.getEditText().getText().toString().trim();
        placeVisitTime = bestVisitTime.getEditText().getText().toString().trim();
        placeBudget = budget.getEditText().getText().toString().trim();
        placeAddress = address.getEditText().getText().toString().trim();

        //short Validation
        if (TextUtils.isEmpty(placeTitle)) {
            title.setError("Title cannot be empty");
            return;
        } else {
            title.setError(null);
        }
        if (TextUtils.isEmpty(placeDescription)) {
            description.setError("Description required");
            return;
        } else {
            description.setError(null);
        }
        if (TextUtils.isEmpty(placeCategory)) {
            categories.setError("Select one category");
            return;
        } else {
            categories.setError(null);
        }
        if (TextUtils.isEmpty(placeVisitTime)) {
            bestVisitTime.setError("Place visit time needed");
            return;
        } else {
            bestVisitTime.setError(null);
        }
        if (TextUtils.isEmpty(placeBudget)) {
            budget.setError("budget is required");
            return;
        } else {
            budget.setError(null);
        }
        if (TextUtils.isEmpty(placeAddress)) {
            address.setError("Address is required!!");
            return;
        } else {
            address.setError(null);
        }

        addPlaceToFirebase();
    }

    private void addPlaceToFirebase() {
        progressDialog.show();

        timestamp = "" + System.currentTimeMillis();

        if (imageUris == null) {
            Toast.makeText(this, "One photo should be uploaded", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String filePathName="place_images/"+""+timestamp;
            StorageReference storageReference= FirebaseStorage.getInstance().getReference(filePathName);
                Uri individualImage=imageUris;
                storageReference.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urlStrings=String.valueOf(uri);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            SessionManager sessionManager=new SessionManager(this,SessionManager.SESSION_USERSESSION);
            HashMap<String,String> userDetails=sessionManager.getUserDetailFromSession();
            phone=userDetails.get(SessionManager.KEY_PHONENUMBER);

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("placeId", "" + timestamp);
            hashMap.put("placeTitle", "" + placeTitle);
            hashMap.put("placeDescription", "" + placeDescription);
            hashMap.put("placeCategory", "" + placeCategory);
            hashMap.put("placeVisitTime", "" + placeVisitTime);
            hashMap.put("placeBudget", "" + placeBudget);
            hashMap.put("placeAddress", "" + placeAddress);
            hashMap.put("userId", "" +phone);
            hashMap.put("placeImage",""+urlStrings);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(phone).child("Places").child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(),"Place added Successfully",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGES_CODES && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            imageUris = data.getData();
            Imagepicker.setImageURI(imageUris);
        }
    }
}