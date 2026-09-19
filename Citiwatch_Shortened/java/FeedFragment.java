package com.example.citiwatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class FeedFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Filter chip toggle logic
        TextView chipAll        = view.findViewById(R.id.chipAll);
        TextView chipEmergency  = view.findViewById(R.id.chipEmergency);
        TextView chipReported   = view.findViewById(R.id.chipReported);
        TextView chipInProgress = view.findViewById(R.id.chipInProgress);
        TextView chipResolved   = view.findViewById(R.id.chipResolved);

        TextView[] chips = {chipAll, chipEmergency, chipReported, chipInProgress, chipResolved};

        for (int i = 0; i < chips.length; i++) {
            final int idx = i;
            chips[i].setOnClickListener(v -> {
                // Reset all chips to unselected style
                for (TextView chip : chips) {
                    chip.setBackgroundResource(R.drawable.bg_chip_unselected);
                    chip.setTextColor(requireContext().getColor(R.color.textGray));
                    chip.setTypeface(null, android.graphics.Typeface.NORMAL);
                }
                // Activate clicked chip
                chips[idx].setBackgroundResource(R.drawable.bg_chip_selected);
                chips[idx].setTextColor(requireContext().getColor(R.color.white));
                chips[idx].setTypeface(null, android.graphics.Typeface.BOLD);

                Toast.makeText(getContext(),
                    "Showing: " + chips[idx].getText(), Toast.LENGTH_SHORT).show();
            });
        }

        // Logout button in header
        view.findViewById(R.id.ivLogout).setOnClickListener(v -> showLogoutDialog());
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout", (dialog, which) -> {
                LoginActivity.registeredEmail    = "";
                LoginActivity.registeredPassword = "";
                LoginActivity.registeredName     = "";
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
}
