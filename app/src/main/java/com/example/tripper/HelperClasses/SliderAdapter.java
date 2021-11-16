package com.example.tripper.HelperClasses;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.tripper.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }
    int images[]={
      R.drawable.slider1,
            R.drawable.slider2,
            R.drawable.backpack,
            R.drawable.travel_world
    };
    int headings[]={
            R.string.slider1_header,
            R.string.slider2_header,
            R.string.slider3_header,
            R.string.slider4_header
    };
    int description[]={
            R.string.slider_desc1,
            R.string.slider_desc2,
            R.string.slider_desc3,
            R.string.slider_desc4
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slides_layout,container,false);
        ImageView imageView=view.findViewById(R.id.slider_img);
        TextView heading=view.findViewById(R.id.slider_heading);
        TextView desc=view.findViewById(R.id.slider_desc);

        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        desc.setText(description[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ConstraintLayout)object).removeView(container);
    }
}
