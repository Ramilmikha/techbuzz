package com.example.techbuzzproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class UpdateSkillsActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_skills); // Ensure this layout exists

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Example skills input field
        EditText skillsInput = findViewById(R.id.skills_input);
        Button updateSkillsButton = findViewById(R.id.update_skills_button);

        // Update skills on button click
        updateSkillsButton.setOnClickListener(v -> {
            String skillsText = skillsInput.getText().toString().trim();
            if (!skillsText.isEmpty()) {
                // Convert comma-separated skills into a List
                List<String> skills = Arrays.asList(skillsText.split("\\s*,\\s*"));
                updateSkillsInFirestore(skills);
            } else {
                Toast.makeText(this, "Please enter your skills", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSkillsInFirestore(List<String> skills) {
        String userId = mAuth.getCurrentUser().getUid();

        // Update the "skills" field in Firestore
        db.collection("users").document(userId).update("skills", skills)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Skills Updated Successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update skills. Try again.", Toast.LENGTH_SHORT).show());
    }
}
