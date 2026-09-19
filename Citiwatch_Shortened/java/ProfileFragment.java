package com.example.citiwatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Show registered user info
        TextView tvName  = view.findViewById(R.id.tvProfileName);
        TextView tvEmail = view.findViewById(R.id.tvProfileEmail);

        if (!LoginActivity.registeredName.isEmpty()) {
            tvName.setText(LoginActivity.registeredName);
        }
        if (!LoginActivity.registeredEmail.isEmpty()) {
            tvEmail.setText(LoginActivity.registeredEmail);
        }

        // Settings row clicks
        view.findViewById(R.id.rowEditProfile).setOnClickListener(v ->
            Toast.makeText(getContext(), "Edit Profile – coming soon", Toast.LENGTH_SHORT).show());

        view.findViewById(R.id.rowNotifications).setOnClickListener(v ->
            Toast.makeText(getContext(), "Notifications settings – coming soon",
                Toast.LENGTH_SHORT).show());

        view.findViewById(R.id.rowPrivacy).setOnClickListener(v ->
            Toast.makeText(getContext(), "Privacy & Security – coming soon",
                Toast.LENGTH_SHORT).show());

        // Logout
        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout", (dialog, which) -> {
                // Clear in-memory credentials
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
