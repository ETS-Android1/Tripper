package com.example.tripper.LocationContributor;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tripper.Databases.SessionManager;
import com.example.tripper.R;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class AddPlaces extends AppCompatActivity {

    private CircleImageView Imagepicker;
    private String encodeImageString;
    private Button addBtn;
    Bitmap bitmap;
    private static final String url="http://192.168.1.32/tripper/addPlaces.php";
    private static final int PICK_IMAGES_CODES = 0;
    private TextInputLayout title, description, categories, bestVisitTime, budget, address;
    private String uid;
    private String placeTitle, placeDescription, placeCategory, placeVisitTime, placeBudget, placeAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);

        addBtn=findViewById(R.id.addBtn);
        Imagepicker = findViewById(R.id.image_selection);
        title = findViewById(R.id.addplace_title);
        description = findViewById(R.id.addplace_description);
        categories = findViewById(R.id.addplace_categories);
        bestVisitTime = findViewById(R.id.addplace_bestVisitTime);
        budget = findViewById(R.id.addplace_budget);
        address = findViewById(R.id.addplace_address);


        String[] Categories = new String[]{"Beaches", "Hill Station", "Island", "Town & Cities"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, R.layout.dropdown_item, Categories);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.autoCompleteTextView);
        editTextFilledExposedDropdown.setAdapter(adapter);

        Imagepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AddPlaces.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Browse Image"),PICK_IMAGES_CODES);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGES_CODES && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            Uri imageUris = data.getData();
            try{
                Imagepicker.setImageURI(imageUris);
                InputStream inputStream=getContentResolver().openInputStream(imageUris);
                bitmap= BitmapFactory.decodeStream(inputStream);
                encodeBitMapImage(bitmap);
            }catch (Exception e){

            }
        }
    }

    private void encodeBitMapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);

    }


    public void callBackScreen(View view) {
        AddPlaces.super.onBackPressed();
    }



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

        addPlaceToDatabase();
    }

    private void addPlaceToDatabase() {
            SessionManager sessionManager=new SessionManager(this,SessionManager.SESSION_USERSESSION);
            HashMap<String,String> userDetails=sessionManager.getUserDetailFromSession();
            uid=userDetails.get(SessionManager.KEY_USERID);

            //Volley
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                title.getEditText().setText("");
                description.getEditText().setText("");
                bestVisitTime.getEditText().setText("");
                budget.getEditText().setText("");
                address.getEditText().setText("");
                Imagepicker.setImageURI(Uri.parse("android.resource://com.example.tripper/drawable/add_100px.png"));
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<>();
                map.put("title", "" + placeTitle);
                map.put("description", "" + placeDescription);
                map.put("category", "" + placeCategory);
                map.put("visitTime", "" + placeVisitTime);
                map.put("budget", "" + placeBudget);
                map.put("address", "" + placeAddress);
                map.put("imagePlace",""+encodeImageString);
                map.put("uid", "" +uid);
                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
        }

}