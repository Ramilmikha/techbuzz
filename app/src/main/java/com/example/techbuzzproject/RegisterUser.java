package com.example.techbuzzproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterUser extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeruser); // Ensure this matches your layout file name

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonSignUp = findViewById(R.id.buttonSignIn); // Change to buttonSignUp for clarity
        TextView textViewLogin = findViewById(R.id.textViewLogin); // For navigating to login

        // Set up the sign-up button click listener
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });

        // Set up the text view for navigating to login
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the RegisterUser activity, returning to Login
            }
        });
    }

    private void handleRegister() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Simple validation
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase registration
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration success
                        Toast.makeText(RegisterUser.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        // Navigate to the Home activity or another relevant activity
                        startActivity(new Intent(RegisterUser.this, LogIn.class));
                        finish(); // Optionally finish the RegisterUser activity
                    } else {
                        // If registration fails, display a message to the user.
                        Toast.makeText(RegisterUser.this, "Registration Failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}