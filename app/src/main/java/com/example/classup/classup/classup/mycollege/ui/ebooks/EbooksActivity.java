package com.example.classup.classup.classup.mycollege.ui.ebooks;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classup.classup.classup.mycollege.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EbooksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EbooksAdapter ebooksAdapter;
    private List<String> titles;
    private List<String> urls;
    private DatabaseReference reference;

    private static final int PERMISSION_REQUEST_CODE = 101;
    private static final int MANAGE_ALL_FILES_ACCESS_REQUEST = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks);

        // Initialize the RecyclerView and lists for titles and URLs
        recyclerView = findViewById(R.id.ebooksRecyclerView);
        titles = new ArrayList<>();
        urls = new ArrayList<>();

        // Add a LayoutManager to the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a reference to Firebase Database for ebooks
        reference = FirebaseDatabase.getInstance().getReference().child("pdf");

        // Check for permissions at runtime based on Android version
        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+
            if (!Environment.isExternalStorageManager()) {
                requestManageAllFilesAccessPermission();
            } else {
                fetchEbooksData();
            }
        } else {
            // Android 10 and below
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            } else {
                fetchEbooksData();
            }
        }
    }

    private void requestManageAllFilesAccessPermission() {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, MANAGE_ALL_FILES_ACCESS_REQUEST);
        } catch (Exception e) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MANAGE_ALL_FILES_ACCESS_REQUEST) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    fetchEbooksData();
                } else {
                    Toast.makeText(this, "Permission denied. You can't access files.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void fetchEbooksData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String pdfTitle = dataSnapshot.child("pdfTitle").getValue(String.class);
                        String pdfUrl = dataSnapshot.child("pdfUrl").getValue(String.class);

                        if (pdfTitle != null && pdfUrl != null) {
                            titles.add(pdfTitle);
                            urls.add(pdfUrl);
                        }
                    }
                    ebooksAdapter = new EbooksAdapter(EbooksActivity.this, titles, urls);
                    recyclerView.setAdapter(ebooksAdapter);
                } else {
                    Toast.makeText(EbooksActivity.this, "No ebooks found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EbooksActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchEbooksData();
            } else {
                Toast.makeText(this, "Permission denied. You can't access or download files.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
