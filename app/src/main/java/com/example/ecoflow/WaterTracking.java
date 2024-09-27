package com.example.ecoflow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class WaterTracking extends AppCompatActivity {

    TextView water_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_water_tracking);

        water_level = findViewById(R.id.water_level);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (true) {
                    water_level.setText("Water level is above threshold");
                }
            }
        }, 10000);

    }

    public void back(View view) {
        Intent intent = new Intent(WaterTracking.this, Dashboard.class);
        startActivity(intent);
    }
}
