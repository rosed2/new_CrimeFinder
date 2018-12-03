package com.example.archi.crimefinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        String getRadius = intent.getStringExtra("com.example.archi.crimefinder.RADIUS");
        int radius = Integer.parseInt(getRadius);
        String getLat = intent.getStringExtra("com.example.archi.crimefinder.LATITUDE");
        Double latitude = Double.parseDouble(getLat);
        String getLong = intent.getStringExtra("com.example.archi.crimefinder.LONGITUDE");
        Double longitude = Double.parseDouble(getLong);

        TextView crimesTitle = (TextView) findViewById(R.id.crimesTitle);
        String title = "Crimes within " + getRadius + " miles of latitude: " + latitude + " and longitude: " + longitude;
        crimesTitle.setText(title);
        Button again_btn  = (Button) findViewById(R.id.again_btn);
        again_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent startIntent = (new Intent(ResultsActivity.this, MainActivity.class));
                 startActivity(startIntent);
             }
         }
        );


    }

}

