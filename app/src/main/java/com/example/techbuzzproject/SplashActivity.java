package com.example.techbuzzproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Ensure this file exists in your layout folder

        mAuth = FirebaseAuth.getInstance();

        // Show the welcome dialog after 2.5 seconds
        new Handler().postDelayed(this::showCustomWelcomeDialog, 2500);
    }

    private void showCustomWelcomeDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_welcome);
        dialog.setCancelable(false);

        TextView title = dialog.findViewById(R.id.titleTextView);
        TextView message = dialog.findViewById(R.id.messageTextView);
        Button btnSignIn = dialog.findViewById(R.id.btnSignIn);
        Button btnViewEvents = dialog.findViewById(R.id.btnViewEvents);
        Button btnLogout = dialog.findViewById(R.id.btnLogout);

        // Set all buttons to be visible
        btnSignIn.setVisibility(View.VISIBLE);    // Show Sign In button
        btnViewEvents.setVisibility(View.VISIBLE); // Show View Events button
        btnLogout.setVisibility(View.VISIBLE);      // Show Logout button

        // Check if user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in
            btnSignIn.setEnabled(false);
            
        } else {
            // User is not signed in
            btnLogout.setEnabled(false); // Optionally disable Logout button
        }

        btnSignIn.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(SplashActivity.this, LogIn.class));
            finish();
        });

        btnViewEvents.setOnClickListener(v -> {
            // Use the already defined currentUser variable
            if (currentUser != null) {
                dialog.dismiss();
                checkUserAndNavigate(); // User is signed in, navigate to events
            } else {
                Toast.makeText(this, "Please sign in first to view events.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                startActivity(new Intent(SplashActivity.this, LogIn.class)); // Redirect to Sign In page
                finish();
            }
        });

        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            showCustomWelcomeDialog();  // Refresh the dialog to reset the buttons
        });

        dialog.show();
    }

    private void checkUserAndNavigate() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User already signed in, go directly to Home
            startActivity(new Intent(SplashActivity.this, Home.class));
            finish();
        } else {
            // This branch should not be hit since we check earlier in btnViewEvents
            Toast.makeText(this, "Please sign in first to view events.", Toast.LENGTH_SHORT).show();
        }
    }
}