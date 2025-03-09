package com.example.classup.classup.classup.mycollege.ui.faculty;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.classup.classup.classup.mycollege.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FacultyFragment extends Fragment {


    private RecyclerView cs, me, ece, ce, mi, bt, fpe, pe, ch, ma;
    private LinearLayout csNoData, meNoData, eceNoData, ceNoData, miNoData, btNoData, fpeNoData, peNoData, chNoData, maNoData;
    private List<TeachersData> list1, list2, list3, list4, list5, list6, list7, list8, list9, list10;
    private TeacherAdapter adapter;
    private DatabaseReference reference,dbRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_faculty, container, false);


        cs = view.findViewById(R.id.cs);
        me = view.findViewById(R.id.me);
        ece = view.findViewById(R.id.ece);
        ce = view.findViewById(R.id.ce);
        mi = view.findViewById(R.id.mi);
        bt = view.findViewById(R.id.bt);
        fpe = view.findViewById(R.id.fpe);
        pe = view.findViewById(R.id.pe);
        ch = view.findViewById(R.id.ch);
        ma = view.findViewById(R.id.ma);

        csNoData = view.findViewById(R.id.csNoData);
        meNoData = view.findViewById(R.id.meNoData);
        eceNoData = view.findViewById(R.id.eceNoData);
        ceNoData = view.findViewById(R.id.ceNoData);
        miNoData = view.findViewById(R.id.miNoData);
        btNoData = view.findViewById(R.id.btNoData);
        fpeNoData = view.findViewById(R.id.fpeNoData);
        peNoData = view.findViewById(R.id.peNoData);
        chNoData = view.findViewById(R.id.chNoData);
        maNoData = view.findViewById(R.id.maNoData);
        reference = FirebaseDatabase.getInstance().getReference();


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();
        list6 = new ArrayList<>();
        list7 = new ArrayList<>();
        list8 = new ArrayList<>();
        list9 = new ArrayList<>();
        list10 = new ArrayList<>();

        cs();
        me();
        ece();
        ce();
        mi();
        bt();
        fpe();
        pe();
        ch();
        ma();

       return  view;
    }

    private void cs() {
        dbRef = reference.child("Computer Science Engineering");
        setRecyclerView(cs, list1, csNoData, "Computer Science Engineering");
    }

    private void me() {
        dbRef = reference.child("Mechanical Engineering");
        setRecyclerView(me, list2, meNoData, "Mechanical Engineering");
    }

    private void ece() {
        dbRef = reference.child("Electronics and Communication Engineering");
        setRecyclerView(ece, list3, eceNoData, "Electronics and Communication Engineering");
    }

    private void ce() {
        dbRef = reference.child("Civil Engineering");
        setRecyclerView(ce, list4, ceNoData, "Civil Engineering");
    }

    private void mi() {
        dbRef = reference.child("Mining Engineering");
        setRecyclerView(mi, list5, miNoData, "Mining Engineering");
    }

    private void bt() {
        dbRef = reference.child("Biotechnology Engineering");
        setRecyclerView(bt, list6, btNoData, "Biotechnology Engineering");
    }

    private void fpe() {
        dbRef = reference.child("Food Processing Engineering");
        setRecyclerView(fpe, list7, fpeNoData, "Food Processing Engineering");
    }

    private void pe() {
        dbRef = reference.child("Physics Engineering");
        setRecyclerView(pe, list8, peNoData, "Physics Engineering");
    }

    private void ch() {
        dbRef = reference.child("Chemistry");
        setRecyclerView(ch, list9, chNoData, "Chemistry");
    }

    private void ma() {
        dbRef = reference.child("Maths");
        setRecyclerView(ma, list10, maNoData, "Maths");
    }

    private void setRecyclerView(RecyclerView recyclerView, List<TeachersData> dataList, LinearLayout noDataLayout, String category) {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                if (!snapshot.exists()) {
                    noDataLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        TeachersData data = snapshot1.getValue(TeachersData.class);
                        dataList.add(data);
                    }
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    TeacherAdapter adapter = new TeacherAdapter(dataList, getContext());
                    recyclerView.setAdapter(adapter);
                    noDataLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
