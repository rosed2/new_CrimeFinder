package com.example.archi.crimefinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String longitude;
    private String latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button radius1mi = (Button) findViewById(R.id.radius1mi);
        radius1mi.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent startIntent = (new Intent(MainActivity.this, ResultsActivity.class));

                 startIntent.putExtra("com.example.archi.crimefinder.RADIUS", "1");
                 EditText enterLatitude = (EditText) findViewById(R.id.enterLatitude);
                 EditText enterLongitude = (EditText) findViewById(R.id.enterLongitude);
                 latitude = enterLatitude.getText().toString();
                 longitude = enterLongitude.getText().toString();
                 startIntent.putExtra("com.example.archi.crimefinder.LATITUDE", latitude);
                 startIntent.putExtra("com.example.archi.crimefinder.LONGITUDE", longitude);
                 startActivity(startIntent);

             }
         }
        );
        Button radius5mi = (Button) findViewById(R.id.radius5mi);
        radius5mi.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent startIntent = (new Intent(MainActivity.this, ResultsActivity.class));
                 startIntent.putExtra("com.example.archi.crimefinder.RADIUS", "5");
                 EditText enterLatitude = (EditText) findViewById(R.id.enterLatitude);
                 EditText enterLongitude = (EditText) findViewById(R.id.enterLongitude);
                 latitude = enterLatitude.getText().toString();
                 longitude = enterLongitude.getText().toString();
                 startIntent.putExtra("com.example.archi.crimefinder.LATITUDE", latitude);
                 startIntent.putExtra("com.example.archi.crimefinder.LONGITUDE", longitude);
                 startActivity(startIntent);
             }
         }
        );
        Button radius10mi = (Button) findViewById(R.id.radius10mi);
        radius10mi.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent startIntent = (new Intent(MainActivity.this, ResultsActivity.class));
                 startIntent.putExtra("com.example.archi.crimefinder.RADIUS", "10");
                 EditText enterLatitude = (EditText) findViewById(R.id.enterLatitude);
                 EditText enterLongitude = (EditText) findViewById(R.id.enterLongitude);
                 latitude = enterLatitude.getText().toString();
                 longitude = enterLongitude.getText().toString();
                 startIntent.putExtra("com.example.archi.crimefinder.LATITUDE", latitude);
                 startIntent.putExtra("com.example.archi.crimefinder.LONGITUDE", longitude);
                 startActivity(startIntent);
             }
         }
        );
    }


}
