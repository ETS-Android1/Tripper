package com.example.tripper.HelperClasses;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tripper.R;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceHolder> {

    private Context context;
    public ArrayList<PlaceModel> placeList;

    public PlaceAdapter(Context context, ArrayList<PlaceModel> placeList) {
        this.context = context;
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout

        View view= LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);
        return new PlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceHolder holder, int position) {
        //get Data
        PlaceModel placeModel=placeList.get(position);
        String title=placeModel.placeTitle;
        String uid=placeModel.placeId;
        String address=placeModel.placeAddress;
        ArrayList<String> icons= placeModel.getImageUrls();
        String icon=icons.get(0);


        //set Data
        holder.titleView.setText(title);
        holder.addressView.setText(address);
        Log.d("Harsh","Uid of place"+uid);
        try {
            Glide.with(holder.itemView.getContext()).load(icon).into(holder.placeIcon);
        }catch (Exception e){
            Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Handle Places Click

            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class PlaceHolder extends RecyclerView.ViewHolder {

        private ImageView placeIcon;
        private TextView titleView;
        private TextView addressView;
        /*Holds Product in Recycler View*/
        public PlaceHolder(@NonNull View itemView) {
            super(itemView);

            placeIcon = itemView.findViewById(R.id.placeIcon);
            titleView=itemView.findViewById(R.id.titleView);
            addressView=itemView.findViewById(R.id.AddressView);
        }
    }

}
