package com.example.tripper.HelperClasses;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tripper.R;
import com.example.tripper.User.UserPlaceInfo;

import java.util.List;

public class myPlaceAdapter extends RecyclerView.Adapter<myPlaceAdapter.myViewHolder> {

    List<responseModelPlaces> data;

    public myPlaceAdapter(List<responseModelPlaces> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myPlaceAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
            responseModelPlaces responseModelPlaces= data.get(position);
            holder.title.setText(responseModelPlaces.getTitle());
            holder.description.setText(responseModelPlaces.getDescription());
        Glide.with(holder.title.getContext()).load("http://192.168.1.32/tripper/images/"
                +responseModelPlaces.getPlaceImage()).into(holder.img);



        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserPlaceInfo.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView title, description;
            LinearLayoutCompat parentLayout;

            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.img);
                title = itemView.findViewById(R.id.titlePlace);
                description = itemView.findViewById(R.id.description_place);
                parentLayout=itemView.findViewById(R.id.parentlayout);
            }
        }
}
