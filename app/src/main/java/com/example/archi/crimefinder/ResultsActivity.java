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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {
    private static RequestQueue requestQueue;
    private String places = "";
    private static final String TAG = "ResultsCallAPI";
    private String city;
    private String state;
    TextView listOfCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        String getCity = intent.getStringExtra("com.example.archi.crimefinder.CITY");
        String[] location = getCity.split(",");
        city = location[0].trim();
        state = location[1].trim();
        listOfCrimes = (TextView) findViewById(R.id.listOfCrimes);

        startAPICall();

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
            String url = "https://developer.nrel.gov/api/alt-fuel-stations/v1/nearest.json?api_key"
                    + "=QvOgqLHvt4NR9MafmiD8StVehCERDT0Y4LE0QDbV&location=" + city + "+" + state;

            Log.d("ResponseURL", url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                String locations = "";
                                Log.d(TAG, response.toString());
                                //JSONObject obj= new JSONObject(response.toString());
                                JSONArray arr = response.getJSONArray("fuel_stations");
                                Log.d("ResultsResponse", response.toString());
                                for (int i = 0; i < arr.length(); i++) {
                                    JSONObject temp = (JSONObject) arr.getJSONObject(i);
                                    locations += temp.get("street_address") + "\n";
                                }
                                listOfCrimes.setText(locations);
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

            };
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

