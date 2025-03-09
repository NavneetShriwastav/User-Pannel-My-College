package com.example.classup.classup.classup.mycollege;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.classup.classup.classup.mycollege.ui.auth.SignIn;
import com.example.classup.classup.classup.mycollege.ui.auth.SignUp;
import com.example.classup.classup.classup.mycollege.ui.cgpaCalc.cgpa;
import com.example.classup.classup.classup.mycollege.ui.ebooks.EbooksActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    NavController navController;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    ImageView insta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconTintList(null);
        navController = Navigation.findNavController(this, R.id.frame_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        drawerLayout = findViewById(R.id.drawerLayout);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_developer) {
            openGmail("navneetshriwastav43@gmail.com");
            return true; // Return true to indicate that the item is selected
        } else if (id == R.id.navigation_cgpa) {
            //showToast("Video Lectures");
            Intent intent = new Intent(MainActivity.this, cgpa.class);
            startActivity(intent);

        } else if (id == R.id.navigation_rate) {
            showToast("Rate Us");
        } else if (id == R.id.navigation_ebooks) {
            Intent intent = new Intent(MainActivity.this, EbooksActivity.class);
            startActivity(intent);
        } else if (id == R.id.logOut) {
            showLogoutDialog();
        } else if (id == R.id.navigation_website) {
            openWebsite("https://www.nitrkl.ac.in/");
            return true; // Return true to indicate that the item is selected
        } else if (id == R.id.navigation_share) {
            shareContent("Share subject", "Share body");
            return true; // Return true to indicate that the item is selected
        }

        drawerLayout.closeDrawers(); // Close the navigation drawer
        return true;
    }


    private void showLogoutDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialogInterface, which) -> {
                    // Clear session data if any
                    SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
                    sharedPreferences.edit().clear().apply();

                    // Redirect to SignIn activity
                    Intent intent = new Intent(MainActivity.this, SignUp.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
                    startActivity(intent);
                    finish(); // Close current activity
                })
                .setNegativeButton("No", (dialogInterface, which) -> dialogInterface.dismiss())
                .create();

        dialog.show();

        // Set color for the "Yes" and "No" buttons
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.textColor)); // Set color for "Yes" button
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.textColor)); // Set color for "No" button
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void shareContent(String subject, String body) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(shareIntent, "Share using"));
    }

    private void openGmail(String recipientEmail) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", recipientEmail, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(intent, "Email us..."));
    }
    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }




}
