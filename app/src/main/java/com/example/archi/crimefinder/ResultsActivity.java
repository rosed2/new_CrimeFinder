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
import android.text.method.ScrollingMovementMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {
    private static RequestQueue requestQueue;
    private static final String TAG = "ResultsCallAPI";
    private String state;
    TextView listOfCrimes;
    private String message = "";
    private JSONObject recentYear;
    private int year;
    private JSONObject correctYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        state = intent.getStringExtra("com.example.archi.crimefinder.STATE");
        String yr = intent.getStringExtra("com.example.archi.crimefinder.YEAR");
        year = Integer.parseInt(yr);
        listOfCrimes = (TextView) findViewById(R.id.listOfCrimes);
        listOfCrimes.setMovementMethod(new ScrollingMovementMethod());

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
            String url = "https://api.usa.gov/crime/fbi/sapi/api/estimates/states/" + state
                    + "?api_key=QvOgqLHvt4NR9MafmiD8StVehCERDT0Y4LE0QDbV";

            Log.d("ResponseURL", url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                Log.d(TAG, response.toString());

                                JSONObject obj= new JSONObject(response.toString());
                                JSONArray arr = response.getJSONArray("results");
                                Log.d("ResultsResponse", response.toString());
                                recentYear = arr.getJSONObject(0);
                                for (int i = 0; i < arr.length(); i++) {
                                    Log.d("ResultName", "" + arr.getJSONObject(i));
                                    JSONObject temp = (JSONObject) arr.getJSONObject(i);
                                    if (temp.getInt("year") > recentYear.getInt("year")) {
                                        recentYear = temp;
                                    }
                                    if (temp.getInt("year") == year) {
                                        correctYear = temp;
                                    }
                                }
                                if (correctYear != null) {
                                    message += "Year: " + correctYear.get("year");
                                    message += "\nViolent Crimes: " + correctYear.getString("violent_crime");
                                    message += "\nHomicides: " + correctYear.getString("homicide");
                                    message += "\nRobberies: " + correctYear.getString("robbery");
                                    message += "\nAggravated Assaults: " + correctYear.getString("aggravated_assault");
                                    message += "\nProperty Crimes: " + correctYear.getString("property_crime");
                                    message += "\nBurglaries: " + correctYear.getString("burglary");
                                    message += "\nLarcenies: " + correctYear.getString("larceny");
                                    message += "\nMotor Vehicle Theft: " + correctYear.getString("motor_vehicle_theft");
                                    message += "\nArson: " + correctYear.getString("arson");
                                    message += "\nRape: " + correctYear.getString("rape_legacy");
                                    listOfCrimes.setText(message);
                                } else {
                                    message += "Year: " + recentYear.get("year");
                                    message += "\nViolent Crimes: " + recentYear.getString("violent_crime");
                                    message += "\nHomicides: " + recentYear.getString("homicide");
                                    message += "\nRobberies: " + recentYear.getString("robbery");
                                    message += "\nAggravated Assaults: " + recentYear.getString("aggravated_assault");
                                    message += "\nProperty Crimes: " + recentYear.getString("property_crime");
                                    message += "\nBurglaries: " + recentYear.getString("burglary");
                                    message += "\nLarcenies: " + recentYear.getString("larceny");
                                    message += "\nMotor Vehicle Theft: " + recentYear.getString("motor_vehicle_theft");
                                    message += "\nArson: " + recentYear.getString("arson");
                                    message += "\nRape: " + recentYear.getString("rape_legacy");
                                    listOfCrimes.setText(message);
                                }

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

