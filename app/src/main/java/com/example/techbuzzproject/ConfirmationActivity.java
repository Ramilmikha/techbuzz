package com.example.techbuzzproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);

        // Initialize UI components
        TextView confirmationMessage = findViewById(R.id.confirmation_message);
        TextView ticketDetails = findViewById(R.id.ticket_details);
        Button backToHome = findViewById(R.id.back_to_home);

        // Retrieve ticket details passed via Intent
        String ticketInfo = getIntent().getStringExtra("TICKET_INFO");
        ticketDetails.setText(ticketInfo);

        // Navigate back to home
        backToHome.setOnClickListener(v -> finish());
    }
}
