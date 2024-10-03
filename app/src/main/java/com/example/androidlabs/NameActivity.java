package com.example.androidlabs;  // Add the package statement

import android.os.Bundle;  // Import the Bundle class
import android.widget.Button;  // Import the Button class
import android.widget.TextView;  // Import the TextView class
import androidx.appcompat.app.AppCompatActivity;  // Import AppCompatActivity

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);  // Ensure 'activity_name.xml' exists

        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        Button buttonThankYou = findViewById(R.id.buttonThankYou);
        Button buttonDontCallMeThat = findViewById(R.id.buttonDontCallMeThat);

        // Retrieve the name passed from MainActivity
        String name = getIntent().getStringExtra("name");
        textViewWelcome.setText("Welcome " + name + "!");

        // Handle button clicks
        buttonThankYou.setOnClickListener(v -> {
            setResult(1);  // User is happy with the name
            finish();
        });

        buttonDontCallMeThat.setOnClickListener(v -> {
            setResult(0);  // User is not happy with the name
            finish();
        });
    }
}
