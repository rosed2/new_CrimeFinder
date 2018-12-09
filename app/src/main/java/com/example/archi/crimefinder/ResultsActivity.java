package com.example.archi.crimefinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        String getLat = intent.getStringExtra("com.example.archi.crimefinder.LATITUDE");
        Double latitude = Double.parseDouble(getLat);
        String getLong = intent.getStringExtra("com.example.archi.crimefinder.LONGITUDE");
        Double longitude = Double.parseDouble(getLong);
        String getStart = intent.getStringExtra("com.example.archi.crimefinder.START");
        String getEnd = intent.getStringExtra("com.example.archi.crimefinder.END");
        String[] startDateStr = getStart.split("/");
        int[] startDate = new int[3];
        for (int i = 0; i < 3; i++) {
            startDate[i] = Integer.parseInt(startDateStr[i]);
        }
        String[] endDateStr = getStart.split("/");
        int[] endDate = new int[3];
        for (int i = 0; i < 3; i++) {
            endDate[i] = Integer.parseInt(endDateStr[i]);
        }

        TextView crimesTitle = (TextView) findViewById(R.id.crimesTitle);
        String title = "Crimes at latitude: " + latitude + " and longitude: " + longitude + " beginning from: " + getStart + " to: " + getEnd;
        crimesTitle.setText(title);
        Button again_btn  = (Button) findViewById(R.id.again_btn);
        again_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent startIntent = (new Intent(ResultsActivity.this, MainActivity.class));
                 startActivity(startIntent);
             }
         }
         String getCrimes = intent.getStringExtra("com.example.archi.crimefinder.CRIMES");

        }
        );


    }

}

