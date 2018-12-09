package com.example.archi.crimefinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {
    private static RequestQueue requestQueue;
    private String crimes = "";
    private static final String TAG = "ResultsCallAPI";
    private double latitude;
    private double longitude;
    private int[] startDate;
    private int[] endDate;
    private TextView listOfCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        String getLat = intent.getStringExtra("com.example.archi.crimefinder.LATITUDE");
        latitude = Double.parseDouble(getLat);
        String getLong = intent.getStringExtra("com.example.archi.crimefinder.LONGITUDE");
        longitude = Double.parseDouble(getLong);
        String getStart = intent.getStringExtra("com.example.archi.crimefinder.START");
        String getEnd = intent.getStringExtra("com.example.archi.crimefinder.END");
        String[] startDateStr = getStart.split("/");
        startDate = new int[3];
        for (int i = 0; i < 3; i++) {
            startDate[i] = Integer.parseInt(startDateStr[i]);
        }
        String[] endDateStr = getStart.split("/");
        endDate = new int[3];
        for (int i = 0; i < 3; i++) {
            endDate[i] = Integer.parseInt(endDateStr[i]);
        }

        TextView crimesTitle = (TextView) findViewById(R.id.crimesTitle);
        String title = "Crimes at latitude: " + latitude + " and longitude: " + longitude + " beginning from: " + getStart + " to: " + getEnd;
        crimesTitle.setText(title);
        startAPICall();
        listOfCrimes = (TextView) findViewById(R.id.listOfCrimes);
        Button again_btn = (Button) findViewById(R.id.again_btn);
        again_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = (new Intent(ResultsActivity.this, MainActivity.class));
                startActivity(startIntent);
            }
        });
    }
    void startAPICall() {
        try {
            String url = "https://jgentes-Crime-Data-v1.p.mashape.com/crime?enddate=" + endDate[0] +
                    "%2F" + endDate[1] + "%2F" + endDate[2] + "&lat=" + latitude +
                    "&long=" + longitude + "&startdate=" + startDate[0] + "%2F" +
                    startDate[1] + "%2F" + startDate[2];

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                Log.d(TAG, response.toString());
                                JSONArray crimeArray = response.getJSONArray(response.toString());
                                String description = "";
                                String datetime = "";
                                String location = "";
                                for (int count = 0; count < crimeArray.length(); count++) {
                                    JSONObject obj = crimeArray.getJSONObject(count);
                                    description = obj.getString("description");
                                    datetime = obj.getString("datetime");
                                    location = obj.getString("location");
                                    crimes += "/n" + description + " at " + datetime + " at location: "
                                            + location;
                                }
                                Log.d("ResultsResponse", response.toString());
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("X-Mashape-Key", "pLODZfqYocmsh1tPjKvuNg99Keifp12qyuSjsnIN5UFo4cLjal");
                    params.put("Accept", "application/json");
                    return params;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

