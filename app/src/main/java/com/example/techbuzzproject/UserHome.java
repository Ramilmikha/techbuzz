package com.example.techbuzzproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

public class UserHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        // Navigate to ViewEvents
        Button viewEventsButton = findViewById(R.id.viewEventsButton);
        viewEventsButton.setOnClickListener(v ->
                startActivity(new Intent(UserHome.this, ViewEvents.class))
        );

        // Navigate to Profile
        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v ->
                startActivity(new Intent(UserHome.this, UserProfileActivity.class))
        );
    }
}
