package com.example.archi.crimefinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String longitude;
    private String latitude;
    private String start_Date;
    private String end_Date;
    private static RequestQueue requestQueue;
    private static final String TAG = "MainActivity";
    private String crimes = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_main);

        Button sub_button = (Button) findViewById(R.id.sub_button);
        sub_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = (new Intent(MainActivity.this, ResultsActivity.class));

                EditText enterLatitude = (EditText) findViewById(R.id.enterLatitude);
                EditText enterLongitude = (EditText) findViewById(R.id.enterLongitude);
                latitude = enterLatitude.getText().toString();
                longitude = enterLongitude.getText().toString();
                startIntent.putExtra("com.example.archi.crimefinder.LATITUDE", latitude);
                startIntent.putExtra("com.example.archi.crimefinder.LONGITUDE", longitude);

                EditText start_date = (EditText) findViewById(R.id.start_Date);
                EditText end_date = (EditText) findViewById(R.id.end_Date);
                start_Date = start_date.getText().toString();
                end_Date = end_date.getText().toString();
                startIntent.putExtra("com.example.archi.crimefinder.START", start_Date);
                startIntent.putExtra("com.example.archi.crimefinder.END", end_Date);

                startIntent.putExtra("com.example.archi.crimefinder.CRIMES", crimes);

                startActivity(startIntent);

            }
        });
    }
    void startAPICall() {
        try {
            String[] startDateStr = start_Date.split("/");
            int[] startDate = new int[3];
            for (int i = 0; i < 3; i++) {
                startDate[i] = Integer.parseInt(startDateStr[i]);
            }
            String[] endDateStr = end_Date.split("/");
            int[] endDate = new int[3];
            for (int i = 0; i < 3; i++) {
                endDate[i] = Integer.parseInt(endDateStr[i]);
            }
            String url = "https://jgentes-Crime-Data-v1.p.mashape.com/crime?enddate=" + endDate[0] +
                    "%2F" + endDate[1] + "%2F" + endDate[2] + "&lat=" + Double.parseDouble(latitude) +
                    "&long=" + Double.parseDouble(latitude) + "&startdate=" + startDate[0] + "%2F" +
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
                                }
                                crimes += "/n" + description + " at " + datetime + " at location: "
                                        + location;
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
                    params.put("X-Mashape-Key", "AIzaSyDpLSvpu8_KDhIDbfI9pHhAlEEKA8Hup0g");
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
