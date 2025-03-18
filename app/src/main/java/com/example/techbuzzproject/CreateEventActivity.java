package com.example.techbuzzproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity {

    private EditText eventNameEditText = findViewById(R.id.phone_edit_text), eventDateEditText = findViewById(R.id.phone_edit_text), eventLocationEditText = findViewById(R.id.event_location);
    private Button createEventButton = findViewById(R.id.create_event_button);
    private FirebaseFirestore db;

    public CreateEventActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        db = FirebaseFirestore.getInstance();

        createEventButton.setOnClickListener(v -> addEventToFirestore());
    }

    private void addEventToFirestore() {
        String name = eventNameEditText.getText().toString();
        String date = eventDateEditText.getText().toString();
        String location = eventLocationEditText.getText().toString();

        if (name.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> event = new HashMap<>();
        event.put("name", name);
        event.put("date", date);
        event.put("location", location);

        db.collection("events").add(event)
                .addOnSuccessListener(documentReference -> Toast.makeText(this, "Event created successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to create event: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
