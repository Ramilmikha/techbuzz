package com.example.techbuzzproject;

import static com.example.techbuzzproject.R.*;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {

    // Declare UI elements
    private TextView nameTextView, emailTextView;
    private EditText phoneEditText, skillsEditText;
    private Button saveButton;

    // Firebase Firestore instance
    private FirebaseFirestore db;
    private String userId = "USER_DOCUMENT_ID"; // Replace with the logged-in user's document ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements AFTER setContentView()
        nameTextView = findViewById(R.id.name_text_view);
        emailTextView = findViewById(R.id.email_text_view);
        phoneEditText = findViewById(R.id.phone_edit_text);
        skillsEditText = findViewById(R.id.skills_edit_text);
        saveButton = findViewById(R.id.save_button);

        // Load user profile data
        loadUserProfile();

        // Handle save button click
        saveButton.setOnClickListener(v -> saveUserProfile());
    }

    private void loadUserProfile() {
        // Reference the user's document in Firestore
        DocumentReference userRef = db.collection("users").document(userId);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                // Get user details from Firestore
                String name = documentSnapshot.getString("name");
                String email = documentSnapshot.getString("email");
                String phone = documentSnapshot.getString("phone");
                String skills = documentSnapshot.getString("skills"); // Assume skills are comma-separated

                // Populate UI with data
                nameTextView.setText(name);
                emailTextView.setText(email);
                phoneEditText.setText(phone);
                skillsEditText.setText(skills);
            } else {
                Toast.makeText(this, "User profile not found.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to load profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void saveUserProfile() {
        // Get updated details from UI
        String updatedPhone = phoneEditText.getText().toString();
        String updatedSkills = skillsEditText.getText().toString();

        // Create a map of updated fields
        Map<String, Object> updates = new HashMap<>();
        updates.put("phone", updatedPhone);
        updates.put("skills", updatedSkills);

        // Update Firestore document
        DocumentReference userRef = db.collection("users").document(userId);
        userRef.update(updates).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Profile updated successfully.", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
