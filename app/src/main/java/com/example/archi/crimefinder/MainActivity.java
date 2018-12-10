package com.example.archi.crimefinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private String state;
    private String year;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sub_button = (Button) findViewById(R.id.sub_button);
        sub_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = (new Intent(MainActivity.this, ResultsActivity.class));

                EditText enterState = (EditText) findViewById(R.id.enterState);
                state = enterState.getText().toString();
                EditText enterYear = (EditText) findViewById(R.id.enterYear);
                year = enterYear.getText().toString();

                startIntent.putExtra("com.example.archi.crimefinder.STATE", state);
                startIntent.putExtra("com.example.archi.crimefinder.YEAR", year);

                startActivity(startIntent);

            }
        });
    }

}
