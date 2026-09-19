package com.example.citiwatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Report an Issue button -> opens ReportFragment
        view.findViewById(R.id.btnReportIssue).setOnClickListener(v ->
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ReportFragment())
                .addToBackStack(null)
                .commit());

        // See All -> opens Feed
        view.findViewById(R.id.tvSeeAll).setOnClickListener(v ->
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new FeedFragment())
                .addToBackStack(null)
                .commit());

        // Bell icon notification
        view.findViewById(R.id.ivBell).setOnClickListener(v ->
            Toast.makeText(getContext(), "No new notifications", Toast.LENGTH_SHORT).show());
    }
}