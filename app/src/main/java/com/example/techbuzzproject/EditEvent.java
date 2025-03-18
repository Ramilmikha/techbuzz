package com.example.techbuzzproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditEvent extends AppCompatActivity {

    // Initialize UI components
    private EditText editEventName = findViewById(R.id.edit_event_name), editEventLocation = findViewById(R.id.edit_event_location), editEventDate = findViewById(R.id.edit_event_date), editEventPrice = findViewById(R.id.edit_event_price);
    private Button saveButton = findViewById(R.id.save_event_button);
    private FirebaseFirestore db;
    private String documentId; // Document ID of the event to be edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Get the event ID from the intent
        documentId = getIntent().getStringExtra("EVENT_ID");
        fetchEventDetails();

        // Set save button listener
        saveButton.setOnClickListener(v -> saveEventDetails());
    }

    private void fetchEventDetails() {
        db.collection("events").document(documentId).get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        Event event = document.toObject(Event.class);
                        if (event != null) {
                            editEventName.setText(event.getName());
                            editEventLocation.setText(event.getLocation());
                            editEventDate.setText(event.getDate());
                            editEventPrice.setText(event.getPrice());
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to fetch event details.", Toast.LENGTH_SHORT).show());
    }

    private void saveEventDetails() {
        String name = editEventName.getText().toString().trim();
        String location = editEventLocation.getText().toString().trim();
        String date = editEventDate.getText().toString().trim();
        String price = editEventPrice.getText().toString().trim();

        if (name.isEmpty() || location.isEmpty() || date.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an update map
        Map<String, Object> updatedEvent = new HashMap<>();
        updatedEvent.put("name", name);
        updatedEvent.put("location", location);
        updatedEvent.put("date", date);
        updatedEvent.put("price", price);

        // Save updated details to Firestore
        db.collection("events").document(documentId).update(updatedEvent)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Event updated successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update event.", Toast.LENGTH_SHORT).show());
    }
}
