package com.example.techbuzzproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Navigate to CreateEvent
        Button createEventButton = findViewById(R.id.createEventButton);
        createEventButton.setOnClickListener(v ->
                startActivity(new Intent(AdminHome.this, CreateEventActivity.class))
        );

        // Navigate to Manage Events
        Button manageEventsButton = findViewById(R.id.manageEventsButton);
        manageEventsButton.setOnClickListener(v ->
                startActivity(new Intent(AdminHome.this, ViewEvents.class))
        );
    }
}
