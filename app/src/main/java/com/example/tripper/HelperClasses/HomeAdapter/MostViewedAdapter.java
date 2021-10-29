package com.example.tripper.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripper.R;

import java.util.ArrayList;

public class MostViewedAdapter extends RecyclerView.Adapter<MostViewedAdapter.MostViewedViewHolder> {
    ArrayList<MostViewedHelperClass> mostViewedLocation;

    public MostViewedAdapter(ArrayList<MostViewedHelperClass> mostViewedLocations){
        this.mostViewedLocation=mostViewedLocations;
    }

    @NonNull
    @Override
    public MostViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.most_viewed_card_design,parent,false);
        MostViewedViewHolder mostViewedViewHolder=new MostViewedViewHolder(view);
        return mostViewedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MostViewedAdapter.MostViewedViewHolder holder, int position) {
    MostViewedHelperClass helperClass=mostViewedLocation.get(position);
    holder.imageView.setImageResource(helperClass.getImage());
    holder.textView.setText(helperClass.getTitle());
    }

    @Override
    public int getItemCount() {
        return mostViewedLocation.size();
    }

    public  static class MostViewedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MostViewedViewHolder(@NonNull View view) {
            super(view);

            imageView = view.findViewById(R.id.most_image);
            textView = view.findViewById(R.id.mv_title);
        }
    }
}
