package com.example.classup.classup.classup.mycollege.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.classup.classup.classup.mycollege.MainActivity;
import com.example.classup.classup.classup.mycollege.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText etEmail, etName, etPw, etId;
    private Button bt;
    private TextView already;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); // Change to your sign-up layout file

        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etPw = findViewById(R.id.etPw);
        etId = findViewById(R.id.etId);
        bt = findViewById(R.id.bt);
        already = findViewById(R.id.al);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String name = etName.getText().toString();
                String password = etPw.getText().toString();
                String uid = etId.getText().toString();

                if (email.isEmpty() || name.isEmpty() || password.isEmpty() || uid.isEmpty() )  {
                    Toast.makeText(SignUp.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                    return; // Exit method if any field is empty
                }

                if(uid.length()!=9){
                    Toast.makeText(SignUp.this, "Please enter valid Roll Number", Toast.LENGTH_SHORT).show();
                    return; // Exit method if any field is empty
                }

                if(password.length()<6){
                    Toast.makeText(SignUp.this, "Please enter password of length at least 6", Toast.LENGTH_SHORT).show();
                    return; // Exit method if any field is empty
                }

                Users users = new Users(email,name,password,uid);

                databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.child(uid).setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SignUp.this,"Sign Up Successfully",Toast.LENGTH_SHORT).show();
                        etEmail.setText("");
                        etName.setText("");
                        etPw.setText("");
                        etId.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
