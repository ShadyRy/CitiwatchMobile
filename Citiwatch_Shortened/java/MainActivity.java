package com.example.citiwatch;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        FloatingActionButton fabReport = findViewById(R.id.fabReport);

        // The center "nav_report" item is replaced visually by the FAB, disable its clickable area
        bottomNav.getMenu().findItem(R.id.nav_report).setEnabled(false);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {
                selectedFragment = new DashboardFragment();
            } else if (id == R.id.nav_issues) {
                selectedFragment = new FeedFragment();
            } else if (id == R.id.nav_map) {
                selectedFragment = new MapFragment();
            } else if (id == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
                return true;
            }
            return false;
        });

        // FAB opens ReportFragment as an overlay
        fabReport.setOnClickListener(v -> {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ReportFragment())
                .addToBackStack(null)
                .commit();
        });

        // Default: load Home tab
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new DashboardFragment())
                .commit();
            bottomNav.setSelectedItemId(R.id.nav_dashboard);
        }
    }
}