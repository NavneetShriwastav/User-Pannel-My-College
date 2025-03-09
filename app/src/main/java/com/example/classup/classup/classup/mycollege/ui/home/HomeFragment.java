package com.example.classup.classup.classup.mycollege.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.classup.classup.classup.mycollege.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ImageView map;
    private ImageView insta;
    private ImageView twitter;
    private ImageView facebook;
    private ImageView linkedin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<SlideModel> imageList = new ArrayList<>(); // Create image list

        // Add images to the list
        imageList.add(new SlideModel("https://www.nitrkl.ac.in/assets/images/engineering-about.jpg", "NITR Main Gate", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://images.careerindia.com/img/2023/07/nitrourkela-1690265325.jpg", "NITR LA Lawn", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://i.ndtvimg.com/i/2017-03/nit-rourkela_650x400_61490093277.jpg", "NITR Main Building", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://educationbytes.in/wp-content/uploads/2020/10/IMG-20201019-WA0046.jpg", "NITR Golden Jublee Building", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://sambadenglish.com/wp-content/uploads/2022/05/NITR3.jpg", "NITR Fest", ScaleTypes.CENTER_CROP));

        // Set up ImageSlider
        ImageSlider imageSlider = rootView.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);

        // Set up onClickListener for the map ImageView
        map = rootView.findViewById(R.id.map);
        insta = rootView.findViewById(R.id.insta);
        facebook = rootView.findViewById(R.id.fb);
        twitter= rootView.findViewById(R.id.twi);
        linkedin = rootView.findViewById(R.id.ld);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }

            private void openMap() {
                // Create Uri for the location
                Uri uri = Uri.parse("geo:0,0?q=National Institute of Technology, Rourkela");
                // Create Intent to view the location in Maps app
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                // Set package to ensure it opens in Google Maps app
                intent.setPackage("com.google.android.apps.maps");
                // Start the activity
                startActivity(intent);
            }
        });


        return rootView;
    }
}
