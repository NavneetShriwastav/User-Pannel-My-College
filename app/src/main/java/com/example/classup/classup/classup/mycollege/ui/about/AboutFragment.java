package com.example.classup.classup.classup.mycollege.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.classup.classup.classup.mycollege.R;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {
    private ViewPager viewPager;
    private BranchAdapter adapter;
    private List<BranchModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        list = new ArrayList<>();
        list.add(new BranchModel(R.drawable.cse, "Computer Science Engineering", "Description for CSE"));
        list.add(new BranchModel(R.drawable.me, "Mechanical Engineering", "Description for Mechanical"));
        list.add(new BranchModel(R.drawable.ece, "Electronics and Communication Engineering", "Description for Electronic"));
        list.add(new BranchModel(R.drawable.ce, "Civil Engineering", "Description for Civil"));
        list.add(new BranchModel(R.drawable.mn, "Mining Engineering", "Description for Mining"));
        list.add(new BranchModel(R.drawable.bt, "Biotechnology Engineering", "Description for Biotech"));
        list.add(new BranchModel(R.drawable.fpe, "Food Processing Engineering", "Description for FPE"));
        list.add(new BranchModel(R.drawable.ph, "Physics Engineering", "Description for Physics"));
        list.add(new BranchModel(R.drawable.ch, "Chemistry", "Description for Chemistry"));
        list.add(new BranchModel(R.drawable.ma, "Maths", "Description for Maths"));

        adapter = new BranchAdapter(getContext(), list);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        ImageView imageView = view.findViewById(R.id.collegeImg);
        Glide.with(getContext())
                .load("https://d2lk14jtvqry1q.cloudfront.net/media/small_National_Institute_of_Technology_Rourkela_cbc972d135_33ec404c12_a173b5a397.png") // Replace with the actual URL of the image
                .into(imageView);

        return view;
    }
}
