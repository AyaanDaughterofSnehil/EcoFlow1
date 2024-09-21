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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoflow.HomeAdapter.RecyclerAdapter;
import com.example.ecoflow.HomeAdapter.RecyclerHelperClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity  {

    private ImageView tracking;

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private LinearLayout linearView;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        //Hooks
        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        linearView = findViewById(R.id.scroll);

        tracking = findViewById(R.id.track);

        recyclerView = findViewById(R.id.recycle_water);

        recyclerView();

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

    private void recyclerView() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        ArrayList<RecyclerHelperClass> recyclerWater = new ArrayList<>();

        recyclerWater.add(new RecyclerHelperClass(R.drawable.recycle2, "Rainwater Harvesting", "Harness nature's gift - Store rainwater for future use."));
        recyclerWater.add(new RecyclerHelperClass(R.drawable.recycle3, "Drip Irrigation", "Grow together - Use drip irrigation to save water."));
        recyclerWater.add(new RecyclerHelperClass(R.drawable.recycle4, "Recycle and Reuse", "The future is in our hands - Recycle and Reuse today."));
        recyclerWater.add(new RecyclerHelperClass(R.drawable.recycle5, "Conserve Water", "Protect our water sources - Keep life flowing."));

        adapter = new RecyclerAdapter(recyclerWater);
        recyclerView.setAdapter(adapter);
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
