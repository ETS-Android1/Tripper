package com.example.tripper.LocationContributor;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tripper.Common.ConnectionAddress;
import com.example.tripper.Databases.SessionManager;
import com.example.tripper.R;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
    int selectedIndex;
    private static final String url= ConnectionAddress.ipaddress+"/addPlaces.php";
    private static final int PICK_IMAGES_CODES = 0;
    private TextInputLayout title, description, categories, bestVisitTime, budget, address;
    private String uid;
    private String placeTitle, placeDescription, placeCategory, placeVisitTime, placeBudget, placeAddress;


    // array lists
    // for the spinner in the format : City_no : City , State. Eg : 144 : New Delhi , India
    ArrayList<String> listSpinner=new ArrayList<String>();
    // to store the city and state in the format : City , State. Eg: New Delhi , India
    ArrayList<String> listAll=new ArrayList<String>();
    // for listing all states
    ArrayList<String> listState=new ArrayList<String>();
    // for listing all cities
    ArrayList<String> listCity=new ArrayList<String>();
    // access all auto complete text views
    AutoCompleteTextView act;


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
        callAll();

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




    public void callAll()
    {
        obj_list();
        addToAll();
    }


    // Get the content of cities.json from assets directory and store it as string
    public String getJson()
    {
        String json=null;
        try
        {
            // Opening cities.json file
            InputStream is = getAssets().open("cities.json");
            // is there any content in the file
            int size = is.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            is.read(buffer);
            // close the stream --- very important
            is.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return json;
        }
        return json;
    }

    // This add all JSON object's data to the respective lists
    void obj_list()
    {
        // Exceptions are returned by JSONObject when the object cannot be created
        try
        {
            // Convert the string returned to a JSON object
            JSONObject jsonObject=new JSONObject(getJson());
            // Get Json array
            JSONArray array=jsonObject.getJSONArray("array");
            // Navigate through an array item one by one
            for(int i=0;i<array.length();i++)
            {
                // select the particular JSON data
                JSONObject object=array.getJSONObject(i);
                String city=object.getString("name");
                String state=object.getString("state");
                // add to the lists in the specified format
                listSpinner.add(String.valueOf(i+1)+" : "+city+" , "+state);
                listAll.add(String.valueOf(i+1)+" : "+city+" , "+state);
                listCity.add(city);
                listState.add(state);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    // The first auto complete text view
    void addToAll()
    {
        act=findViewById(R.id.actAll);
        adapterSetting(listAll);
    }



    // setting adapter for auto complete text views
    void adapterSetting(ArrayList arrayList)
    {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayList);
        act.setAdapter(adapter);
        hideKeyBoard();
    }

    // hide keyboard on selecting a suggestion
    public void hideKeyBoard()
    {
        act.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
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

        String city=act.getText().toString();
        selectedIndex= Integer.parseInt(city.replaceAll("[^0-9]", ""));
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
                Log.d("Harsh",""+selectedIndex+placeTitle+placeCategory);
                Map<String, String> map=new HashMap<>();
                map.put("title", "" + placeTitle);
                map.put("description", "" + placeDescription);
                map.put("category", "" + placeCategory);
                map.put("visitTime", "" + placeVisitTime);
                map.put("budget", "" + placeBudget);
                map.put("address", "" + placeAddress);
                map.put("imagePlace",""+encodeImageString);
                map.put("uid", "" +uid);
                map.put("locationId",""+selectedIndex);
                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
        }

}