package com.example.citiwatch;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private ImageView ivTogglePassword;
    private boolean passwordVisible = false;

    // Simple in-memory credentials set by SignUpActivity
    static String registeredEmail = "";
    static String registeredPassword = "";
    static String registeredName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail           = findViewById(R.id.etEmail);
        etPassword        = findViewById(R.id.etPassword);
        ivTogglePassword  = findViewById(R.id.ivTogglePassword);
        Button btnLogin   = findViewById(R.id.btnLogin);
        TextView tvSignUp = findViewById(R.id.tvSignUp);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);

        ivTogglePassword.setOnClickListener(v -> togglePassword());

        btnLogin.setOnClickListener(v -> attemptLogin());

        tvSignUp.setOnClickListener(v ->
            startActivity(new Intent(this, SignUpActivity.class)));

        tvForgotPassword.setOnClickListener(v ->
            Toast.makeText(this, "Password reset not available in demo version.",
                Toast.LENGTH_SHORT).show());
    }

    private void togglePassword() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            etPassword.setTransformationMethod(null);
            ivTogglePassword.setImageResource(R.drawable.ic_eye_off);
        } else {
            etPassword.setTransformationMethod(new PasswordTransformationMethod());
            ivTogglePassword.setImageResource(R.drawable.ic_eye);
        }
        // Keep cursor at end
        etPassword.setSelection(etPassword.getText().length());
    }

    private void attemptLogin() {
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        // --- Validation ---
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }
        if (!isValidEmail(email)) {
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        // Accept demo credentials OR credentials registered this session
        boolean validDemo = email.equals("demo@citiwatch.com") && password.equals("Demo@1234");
        boolean validRegistered = !registeredEmail.isEmpty()
                && email.equalsIgnoreCase(registeredEmail)
                && password.equals(registeredPassword);

        if (validDemo || validRegistered) {
            // Set name for profile
            if (validDemo) registeredName = "CitiWatch Demo";
            Toast.makeText(this, "Welcome to CitiWatch!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this,
                "Invalid credentials.\nDemo: demo@citiwatch.com / Demo@1234",
                Toast.LENGTH_LONG).show();
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(regex, email);
    }
}