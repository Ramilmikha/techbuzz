package com.example.techbuzzproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeruser); // Ensure this matches your layout file name

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonSignUp = findViewById(R.id.buttonSignIn); // Should be buttonSignUp for clarity
        TextView textViewLogin = findViewById(R.id.textViewLogin); // For navigating back to login

        // Set up the sign-up button click listener
        buttonSignUp.setOnClickListener(v -> handleRegister());

        // Set up the text view click listener to navigate back to login
        textViewLogin.setOnClickListener(v -> finish());
    }

    private void handleRegister() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Basic input validation
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase authentication and saving user to Firestore
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();

                        // Add user data with role "user" by default
                        Map<String, Object> user = new HashMap<>();
                        user.put("email", email);
                        user.put("role", "user"); // Change to "admin" manually during admin registration

                        db.collection("users").document(userId).set(user)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterUser.this, LogIn.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> Toast.makeText(this, "Error saving user data. Please try again.", Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(this, "Registration Failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
