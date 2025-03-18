package com.example.techbuzzproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogIn extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db; // Initialize Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Ensure this matches your layout file name

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonSignIn = findViewById(R.id.buttonlogin);
        TextView textViewLogin = findViewById(R.id.textViewLogin);

        // Set up the sign-in button click listener
        buttonSignIn.setOnClickListener(v -> handleLogin());

        // Set up the text view for navigating to sign-in
        textViewLogin.setOnClickListener(v ->
                startActivity(new Intent(LogIn.this, RegisterUser.class)) // Navigate to SignIn activity
        );
    }

    private void handleLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Simple validation
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // If login succeeds, fetch the role
                        fetchUserRole();
                    } else {
                        // If sign-in fails, display a message to the user
                        Toast.makeText(this, "Login Failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchUserRole() {
        // Get the currently signed-in user's ID
        String userId = mAuth.getCurrentUser().getUid();

        // Access Firestore and retrieve the user's role
        db.collection("users").document(userId).get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        String role = document.getString("role");

                        // Navigate based on role
                        if ("admin".equals(role)) {
                            startActivity(new Intent(LogIn.this, AdminHome.class));
                        } else if ("user".equals(role)) {
                            startActivity(new Intent(LogIn.this, UserHome.class));
                        } else {
                            Toast.makeText(this, "Role not assigned. Please contact support.", Toast.LENGTH_SHORT).show();
                        }
                        finish(); // Optionally finish the Login activity
                    } else {
                        Toast.makeText(this, "Error: User data not found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to fetch user role. Please try again.", Toast.LENGTH_SHORT).show()
                );
    }
}
