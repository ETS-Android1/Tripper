package com.example.tripper.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripper.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.AdapterAllCategoriesViewHolder> {
    ArrayList<CategoriesHelperClass> categories;

    public CategoriesAdapter(ArrayList<CategoriesHelperClass> categories){
        this.categories=categories;
    }


    @NonNull
    @Override
    public CategoriesAdapter.AdapterAllCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_card_design,parent,false);
        AdapterAllCategoriesViewHolder lvh=new AdapterAllCategoriesViewHolder(view);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAllCategoriesViewHolder holder, int position) {

        CategoriesHelperClass helperClass = categories.get(position);
        holder.imageView.setImageResource(helperClass.getImage());
        holder.textView.setText(helperClass.getTitle());
        holder.relativeLayout.setBackground(helperClass.getGradientDrawable());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public static class AdapterAllCategoriesViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        ImageView imageView;
        TextView textView;


        public AdapterAllCategoriesViewHolder(@NonNull View view){
            super(view);

            relativeLayout=view.findViewById(R.id.categories_background);
            imageView=view.findViewById(R.id.categories_image);
            textView=view.findViewById(R.id.categories_title);
        }
    }
}