package com.example.tripper.LocationContributor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.tripper.Common.LoginSignUp.Login;
import com.example.tripper.Databases.SessionManager;
import com.example.tripper.R;
import com.example.tripper.User.UserDashboard;

public class ContributorDashboardFragment extends Fragment {
    ImageView nav_logout,nav_home;
    CardView addTouristPlace,addHotels,addShops,addTravelMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contributor_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        nav_logout= (ImageView) view.findViewById(R.id.nav_logout);
        nav_home=(ImageView) view.findViewById(R.id.nav_home);
        addHotels=(CardView) view.findViewById(R.id.addHotels);
        addTouristPlace=(CardView) view.findViewById(R.id.addTouristPlace);
        addShops=(CardView) view.findViewById(R.id.addShops);
        addTravelMode=(CardView)view.findViewById(R.id.travelMode);
       nav_logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SessionManager sessionManager=new SessionManager(getContext(),SessionManager.SESSION_USERSESSION);
               sessionManager.logoutUserFromSession();
               startActivity(new Intent(getContext(), Login.class));
               getActivity().finish();
           }
       });

       nav_home.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getContext(),UserDashboard.class));
           }
       });

       addTravelMode.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });
       addHotels.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });
       addShops.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });
       addTouristPlace.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });
    }

}