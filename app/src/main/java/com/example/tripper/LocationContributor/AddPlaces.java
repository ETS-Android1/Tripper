package com.example.tripper.LocationContributor;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class AddPlaces extends AppCompatActivity {

    private CircleImageView Imagepicker;
    private ArrayList<Uri> imageUris;
    private ArrayList<String> urlStrings;
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
        imageUris = new ArrayList<>();

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
        urlStrings = new ArrayList<>();

        if (imageUris.size() == 0) {
            Toast.makeText(this, "Atleast one phone should be uploaded", Toast.LENGTH_SHORT).show();
            return;
        } else {
            int image_count;
            String filePathName="place_images/"+""+timestamp;
            StorageReference storageReference= FirebaseStorage.getInstance().getReference(filePathName);

            for(image_count=0;image_count<imageUris.size();image_count++){
                Uri individualImage=imageUris.get(image_count);
                storageReference.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urlStrings.add(String.valueOf(uri));
                                if (urlStrings.size() == imageUris.size()){
                                    storeLink(urlStrings);
                                }
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

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(phone).child("Places").child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(),"Place added Successfully",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private void storeLink(ArrayList<String> urlStrings) {

        HashMap<String, String> hashMap = new HashMap<>();

        for (int i = 0; i <urlStrings.size() ; i++) {
            hashMap.put("ImgLink"+i, urlStrings.get(i));

        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.child(phone).child("Places").child(timestamp).child("PlaceImg").setValue(hashMap)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.dismiss();

        imageUris.clear();
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