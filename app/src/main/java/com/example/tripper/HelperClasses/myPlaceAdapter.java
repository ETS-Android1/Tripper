package com.example.tripper.HelperClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tripper.R;
import com.example.tripper.User.Destination;

import java.util.ArrayList;
import java.util.List;

public class myPlaceAdapter extends RecyclerView.Adapter<myPlaceAdapter.myViewHolder> implements Filterable {

    List<responseModelPlaces> data;
    Context context;
    List<responseModelPlaces> backup;

    public myPlaceAdapter(List<responseModelPlaces> data, Context context) {
        this.data = data;
        this.context = context;
        backup=new ArrayList<>(data);
    }

    @NonNull
    @Override
    public myPlaceAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,
                parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        responseModelPlaces responseModelPlaces = data.get(position);
        holder.title.setText(responseModelPlaces.getTitle());
        holder.description.setText(String.format("%s, %s", responseModelPlaces.getName(),
                responseModelPlaces.getState()));
        Glide.with(holder.title.getContext()).load("http://192.168.1.32/tripper/images/"
                + responseModelPlaces.getPlaceImage()).into(holder.img);


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Destination.class);
                intent.putExtra("placeId", data.get(position).getPlaceId());
                intent.putExtra("location",data.get(position).getName()+","+data.get(position).getState());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<responseModelPlaces> filterdata=new ArrayList<>();
            if(charSequence.toString().isEmpty())
                filterdata.addAll(backup);
            else{
                for (responseModelPlaces obj:backup){
                    if(obj.getTitle().toString().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || obj.getName().toLowerCase().contains(charSequence.toString().toLowerCase())||
                    obj.getState().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        filterdata.add(obj);
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterdata;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
              data.clear();
              data.addAll((ArrayList<responseModelPlaces>)filterResults.values);
              notifyDataSetChanged();
        }
    };

    public static class myViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, description;
        CardView parentLayout;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.titlePlace);
            description = itemView.findViewById(R.id.description_place);
            parentLayout=itemView.findViewById(R.id.cardLayoutPlace);
        }
    }
}
