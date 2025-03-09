package com.example.classup.classup.classup.mycollege.ui.notice;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.classup.classup.classup.mycollege.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class NoticeFragment extends Fragment {
    private RecyclerView deleteNoticeRecycler;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private NoticeAdapter adapter;
    private DatabaseReference reference;


    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        deleteNoticeRecycler = view.findViewById(R.id.deleteNoticeRecycler);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.parseColor("#D33808")));

        // Initialize the reference to the Firebase database
        reference = FirebaseDatabase.getInstance().getReference("Notice"); // Replace "Notices" with your actual database path

        deleteNoticeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        deleteNoticeRecycler.setHasFixedSize(true);

        getNotice();
        return view;
    }

    private void getNotice() {
        // Show the progress bar before starting to fetch data
        progressBar.setVisibility(View.VISIBLE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                    NoticeData data = snapshot1.getValue(NoticeData.class);
                    list.add(data);
                }
                // Reverse the list to display recent notices at the top
                Collections.reverse(list);

                adapter = new NoticeAdapter(getContext(), list);
                deleteNoticeRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                // Hide the progress bar after data is loaded
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Hide the progress bar and show an error message if the data fetch fails
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}


