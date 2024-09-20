package com.example.ecoflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity  {

    private ImageView tracking;

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private LinearLayout linearView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        linearView = findViewById(R.id.scroll);

        tracking = findViewById(R.id.track);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.navHome) {
                    loadFragment(new HomeFragment(), false);
                    linearView.setVisibility(View.VISIBLE);
                } else if (itemId == R.id.navAbout) {
                    loadFragment(new AboutFragment(), false);
                    linearView.setVisibility(View.GONE);
                } else if (itemId == R.id.navFeedback) {
                    loadFragment(new FeedbackFragment(), false);
                    linearView.setVisibility(View.GONE);
                } else if (itemId == R.id.navProfile) {
                    loadFragment(new ProfileFragment(), false);
                    linearView.setVisibility(View.GONE);
                }

                return true;
            }
        });

        loadFragment(new HomeFragment(), true);

        tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, WaterTracking.class);
                startActivity(intent);
            }
        });
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }

        fragmentTransaction.commit();
    }
}
