package com.example.techbuzzproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BuyTicket extends AppCompatActivity {

    private TextView eventDetailsTextView;
    private EditText nameInput, emailInput, phoneInput;
    private Button purchaseButton; // Declare without initializing here
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_ticket);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        eventDetailsTextView = findViewById(R.id.event_details);
        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email_input);
        phoneInput = findViewById(R.id.phone_input);
        purchaseButton = findViewById(R.id.purchase_button); // Initialize here

        // Retrieve event details from Intent
        String eventDetails = getIntent().getStringExtra("EVENT_DETAILS");
        if (eventDetails != null) {
            eventDetailsTextView.setText(eventDetails);
        } else {
            eventDetailsTextView.setText("No event details provided.");
        }

        // Set purchase button listener
        purchaseButton.setOnClickListener(v -> handlePurchase());
    }

    private void handlePurchase() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();

        // Basic validation
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save ticket purchase details to Firestore
        Map<String, Object> ticket = new HashMap<>();
        ticket.put("name", name);
        ticket.put("email", email);
        ticket.put("phone", phone);
        ticket.put("eventDetails", eventDetailsTextView.getText().toString());

        db.collection("tickets").add(ticket)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Ticket Purchased Successfully!", Toast.LENGTH_SHORT).show();
                    // Redirect to a confirmation screen or back to events
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Purchase failed. Try again.", Toast.LENGTH_SHORT).show());
    }
}
