package com.example.classup.classup.classup.mycollege.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classup.classup.classup.mycollege.R;
import com.example.classup.classup.classup.mycollege.ui.gallery.GalleryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class GalleryFragment extends Fragment {
    private RecyclerView convoRecycler, indepRecycler, othRecycler;
    private GalleryAdapter convoAdapter, indepAdapter, othAdapter;
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        convoRecycler = view.findViewById(R.id.convoRecycler);
        indepRecycler = view.findViewById(R.id.indpRecycler);
        othRecycler = view.findViewById(R.id.othRecycler);

        reference = FirebaseDatabase.getInstance().getReference().child("gallery");

        // Retrieve and display images for each category
        getImagesForCategory("Convocation", convoRecycler);
        getImagesForCategory("Independence Day", indepRecycler);
        getImagesForCategory("Other Events", othRecycler);

        return view;
    }

    private void getImagesForCategory(String category, RecyclerView recyclerView) {
        Query query = reference.child(category).orderByChild("timestamp"); // Assuming timestamp is used
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<String> imageList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.getValue() instanceof String) {
                            // If the value is a String, add it directly to the image list
                            String imageUrl = dataSnapshot.getValue(String.class);
                            imageList.add(imageUrl);
                        } else if (dataSnapshot.getValue() instanceof HashMap) {
                            // If the value is a HashMap, parse it to get the URL
                            HashMap<String, Object> imageMap = (HashMap<String, Object>) dataSnapshot.getValue();
                            String imageUrl = (String) imageMap.get("imageUrl");
                            if (imageUrl != null) {
                                imageList.add(imageUrl);
                            }
                        }
                    }

                    // Reverse the list to display recent images first
                    Collections.reverse(imageList);

                    // Initialize adapter based on RecyclerView
                    if (category.equals("Convocation")) {
                        convoAdapter = new GalleryAdapter(getContext(), imageList);
                        convoRecycler.setLayoutManager(new GridLayoutManager(getContext(), 4));
                        convoRecycler.setAdapter(convoAdapter);
                    } else if (category.equals("Independence Day")) {
                        indepAdapter = new GalleryAdapter(getContext(), imageList);
                        indepRecycler.setLayoutManager(new GridLayoutManager(getContext(), 4));
                        indepRecycler.setAdapter(indepAdapter);
                    } else if (category.equals("Other Events")) {
                        othAdapter = new GalleryAdapter(getContext(), imageList);
                        othRecycler.setLayoutManager(new GridLayoutManager(getContext(), 4));
                        othRecycler.setAdapter(othAdapter);
                    }
                } else {
                    Toast.makeText(getContext(), "No images found for " + category, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to retrieve images: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
