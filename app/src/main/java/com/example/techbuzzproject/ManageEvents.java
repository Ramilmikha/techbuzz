package com.example.techbuzzproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ManageEvents extends AppCompatActivity {

    private FirebaseFirestore db;
    private LinearLayout eventListLayout;
    private List<Event> events;
    private List<String> eventIds; // Store document IDs for easy reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_events);

        // Initialize Firestore and UI components
        db = FirebaseFirestore.getInstance();
        eventListLayout = findViewById(R.id.event_list_layout);

        // Fetch all events from Firestore for the admin
        fetchAllEvents();
    }

    private void fetchAllEvents() {
        db.collection("events").get()
                .addOnSuccessListener(querySnapshot -> {
                    events = new ArrayList<>();
                    eventIds = new ArrayList<>();
                    eventListLayout.removeAllViews();

                    for (DocumentSnapshot document : querySnapshot) {
                        Event event = document.toObject(Event.class);
                        if (event != null) {
                            events.add(event);
                            eventIds.add(document.getId()); // Save document ID for reference
                            createEventCard(event, document.getId());
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to fetch events.", Toast.LENGTH_SHORT).show());
    }

    private void createEventCard(Event event, String documentId) {
        // Create a TextView for the event
        TextView eventCard = new TextView(this);
        eventCard.setText("Event: " + event.getName() + "\nLocation: " + event.getLocation() +
                "\nDate: " + event.getDate() + "\nPrice: " + event.getPrice());
        eventCard.setPadding(16, 16, 16, 16);
        eventCard.setBackgroundResource(R.drawable.card_background);

        // Add an edit button for each event
        Button editButton = new Button(this);
        editButton.setText("Edit Event");
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(ManageEvents.this, EditEvent.class);
            intent.putExtra("EVENT_ID", documentId);
            startActivity(intent);
        });

        // Add a delete button for each event
        Button deleteButton = new Button(this);
        deleteButton.setText("Delete Event");
        deleteButton.setOnClickListener(v -> deleteEvent(documentId));

        // Add event card and buttons to the layout
        LinearLayout cardLayout = new LinearLayout(this);
        cardLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.setPadding(16, 16, 16, 16);
        cardLayout.addView(eventCard);
        cardLayout.addView(editButton);
        cardLayout.addView(deleteButton);

        eventListLayout.addView(cardLayout);
    }

    private void deleteEvent(String documentId) {
        db.collection("events").document(documentId).delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Event deleted successfully.", Toast.LENGTH_SHORT).show();
                    fetchAllEvents(); // Refresh the list
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete event.", Toast.LENGTH_SHORT).show());
    }
}
