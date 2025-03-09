package com.example.classup.classup.classup.mycollege.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.classup.classup.classup.mycollege.MainActivity;
import com.example.classup.classup.classup.mycollege.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {
    private static final String PREFS_NAME = "MyCollegePrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_NAME = "userName";

    private DatabaseReference databaseReference;
    private EditText uid;
    private Button btn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is already logged in
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
        if (isLoggedIn) {
            String userName = sharedPreferences.getString(KEY_USER_NAME, "User");
            navigateToMainActivity(userName);
            return; // Skip showing the login screen
        }

        setContentView(R.layout.activity_sign_in);

        uid = findViewById(R.id.etId);
        btn = findViewById(R.id.bt);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = uid.getText().toString();
                if (userId.isEmpty()) {
                    Toast.makeText(SignIn.this, "Please Enter your Roll No", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.child(userId).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        progressBar.setVisibility(View.INVISIBLE); // Hide progress bar on success
                        if (dataSnapshot.exists()) {
                            Toast.makeText(SignIn.this, "User Exist", Toast.LENGTH_SHORT).show();
                            String userName = dataSnapshot.child("name").getValue(String.class);

                            // Save login status in SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(KEY_IS_LOGGED_IN, true);
                            editor.putString(KEY_USER_NAME, userName);
                            editor.apply();

                            navigateToMainActivity(userName);
                        } else {
                            Toast.makeText(SignIn.this, "User doesn't Exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE); // Hide progress bar on failure
                        Toast.makeText(SignIn.this, "Couldn't fetch the data..", Toast.LENGTH_SHORT).show();
                        Log.e("SignIn", "Error fetching data: " + e.getMessage());
                    }
                });
            }
        });
    }

    private void navigateToMainActivity(String userName) {
        Intent intent = new Intent(SignIn.this, MainActivity.class);
        intent.putExtra("key", userName);
        startActivity(intent);
        finish();
    }
}
