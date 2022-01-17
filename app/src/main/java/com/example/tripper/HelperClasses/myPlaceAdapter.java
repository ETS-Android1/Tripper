package com.example.tripper.HelperClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tripper.R;

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
        Glide.with(holder.title.getContext()).load("http://192.168.1.32/tripper/images/"+responseModelPlaces.getPlaceImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView title, description;

            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.img);
                title = itemView.findViewById(R.id.titlePlace);
                description = itemView.findViewById(R.id.description_place);
            }
        }
}
