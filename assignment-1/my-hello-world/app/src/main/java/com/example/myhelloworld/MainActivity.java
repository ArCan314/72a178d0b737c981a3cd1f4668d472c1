package com.example.myhelloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.button);
        final TextView stars = findViewById(R.id.stars);
        RatingBar rb = findViewById(R.id.ratingBar);
        Switch swt = findViewById(R.id.switch1);
        ProgressBar pb = findViewById(R.id.progressBar);

        btn1.setOnClickListener((v) -> {
            Log.d("Button", "Button clicked.");
        });

        rb.setOnRatingBarChangeListener((u,v,w) ->{
            stars.setText(Float.toString(rb.getRating()));
            Log.d("RatingBar", "Set Rating!");
        });

        swt.setOnClickListener((v) -> {
            if (pb.getVisibility() == View.INVISIBLE) {
                pb.setVisibility(View.VISIBLE);
                Log.d("ProgressBarControl", "VISIBLE!");
            }
            else {
                pb.setVisibility(View.INVISIBLE);
                Log.d("ProgressBarControl", "INVISIBLE!");
            }
        });

        Log.d("MainActivity", "Hello World!");

    }
}
