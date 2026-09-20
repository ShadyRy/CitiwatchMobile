package com.example.citiwatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReportFragment extends Fragment {

    private String selectedDate = "";
    private float urgencyRating = 2f;

    // Category spinner options
    private final String[] categories = {
        "Public Safety",
        "Flood / Water Damage",
        "Road Damage",
        "Broken Street Light",
        "Fire Hazard",
        "Illegal Dumping",
        "Power Outage",
        "Fallen Tree",
        "Electrical Hazard",
        "Other"
    };

    // Urgency level spinner options (matching the design)
    private final String[] urgencyLevels = {
        "Low - Can wait",
        "Moderate - Needs attention",
        "High - Urgent",
        "Critical / Emergency"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // --- Category Spinner ---
        Spinner spinnerCategory = view.findViewById(R.id.spinnerCategory);
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(
            requireContext(), android.R.layout.simple_spinner_item, categories);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(catAdapter);

        // --- Urgency Spinner ---
        Spinner spinnerUrgency = view.findViewById(R.id.spinnerUrgency);
        ArrayAdapter<String> urgAdapter = new ArrayAdapter<>(
            requireContext(), android.R.layout.simple_spinner_item, urgencyLevels);
        urgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUrgency.setAdapter(urgAdapter);

        // --- RatingBar ---
        RatingBar ratingBar     = view.findViewById(R.id.ratingBarUrgency);
        TextView tvUrgencyLabel = view.findViewById(R.id.tvUrgencyLabel);
        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            urgencyRating = rating;
            tvUrgencyLabel.setText(getUrgencyLabel(rating));
        });

        // --- CalendarView ---
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        selectedDate = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
                .format(Calendar.getInstance().getTime());
        calendarView.setOnDateChangeListener((cal, year, month, dayOfMonth) -> {
            Calendar picked = Calendar.getInstance();
            picked.set(year, month, dayOfMonth);
            selectedDate = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
                    .format(picked.getTime());
        });

        // --- Photo upload tap ---
        view.findViewById(R.id.llAddPhoto).setOnClickListener(v ->
            Toast.makeText(getContext(), "Photo picker – coming in next version",
                Toast.LENGTH_SHORT).show());

        // --- Auto-GPS button ---
        Button btnGps       = view.findViewById(R.id.btnAutoGps);
        EditText etLocation = view.findViewById(R.id.etLocation);
        btnGps.setOnClickListener(v -> {
            // Simulate GPS location (no runtime permission needed for demo)
            etLocation.setText("Bacolod City, Negros Occidental (GPS)");
            Toast.makeText(getContext(), "Location detected via GPS",
                Toast.LENGTH_SHORT).show();
        });

        // --- Submit ---
        Button btnSubmit   = view.findViewById(R.id.btnSubmitReport);
        EditText etTitle   = view.findViewById(R.id.etIssueTitle);
        EditText etDesc    = view.findViewById(R.id.etDescription);

        btnSubmit.setOnClickListener(v -> {
            String title    = etTitle.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String desc     = etDesc.getText().toString().trim();
            int catPos      = spinnerCategory.getSelectedItemPosition();
            String urgency  = urgencyLevels[spinnerUrgency.getSelectedItemPosition()];

            if (title.isEmpty()) {
                etTitle.setError("Issue title is required");
                etTitle.requestFocus();
                return;
            }
            if (location.isEmpty()) {
                etLocation.setError("Location is required");
                etLocation.requestFocus();
                return;
            }
            if (desc.isEmpty()) {
                etDesc.setError("Description is required");
                etDesc.requestFocus();
                return;
            }

            String msg = "Report Submitted!\n"
                + "Title: "    + title                   + "\n"
                + "Category: " + categories[catPos]       + "\n"
                + "Urgency: "  + urgency                  + "\n"
                + "Location: " + location                  + "\n"
                + "Date: "     + selectedDate;

            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private String getUrgencyLabel(float rating) {
        if (rating <= 1) return "Very Low";
        if (rating <= 2) return "Low";
        if (rating <= 3) return "Moderate";
        if (rating <= 4) return "High";
        return "Critical / Emergency";
    }
}