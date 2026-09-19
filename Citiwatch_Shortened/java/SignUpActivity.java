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

public class SignUpActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etConfirmPassword;
    private ImageView ivTogglePassword, ivToggleConfirmPassword;
    private boolean pwVisible = false, cpwVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail                 = findViewById(R.id.etEmail);
        etPassword              = findViewById(R.id.etPassword);
        etConfirmPassword       = findViewById(R.id.etConfirmPassword);
        ivTogglePassword        = findViewById(R.id.ivTogglePassword);
        ivToggleConfirmPassword = findViewById(R.id.ivToggleConfirmPassword);
        Button btnRegister      = findViewById(R.id.btnRegister);
        TextView tvLoginLink    = findViewById(R.id.tvLoginLink);

        // Toggle password visibility
        ivTogglePassword.setOnClickListener(v -> {
            pwVisible = !pwVisible;
            etPassword.setTransformationMethod(
                pwVisible ? null : new PasswordTransformationMethod());
            ivTogglePassword.setImageResource(
                pwVisible ? R.drawable.ic_eye_off : R.drawable.ic_eye);
            etPassword.setSelection(etPassword.getText().length());
        });

        // Toggle confirm password visibility
        ivToggleConfirmPassword.setOnClickListener(v -> {
            cpwVisible = !cpwVisible;
            etConfirmPassword.setTransformationMethod(
                cpwVisible ? null : new PasswordTransformationMethod());
            ivToggleConfirmPassword.setImageResource(
                cpwVisible ? R.drawable.ic_eye_off : R.drawable.ic_eye);
            etConfirmPassword.setSelection(etConfirmPassword.getText().length());
        });

        btnRegister.setOnClickListener(v -> attemptRegister());

        tvLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void attemptRegister() {
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirm  = etConfirmPassword.getText().toString();
        CheckBox cbTerms = findViewById(R.id.cbTerms);

        // --- Email validation ---
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", email)) {
            etEmail.setError("Enter a valid email address (e.g. juan@email.com)");
            etEmail.requestFocus();
            return;
        }

        // --- Password: minimum 8 characters, must have uppercase, lowercase, digit ---
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }
        if (!isStrongPassword(password)) {
            etPassword.setError(
                "At least 8 characters, 1 uppercase, 1 lowercase, 1 digit, 1 special character");
            etPassword.requestFocus();
            return;
        }

        // --- Confirm password ---
        if (confirm.isEmpty()) {
            etConfirmPassword.setError("Please verify your password");
            etConfirmPassword.requestFocus();
            return;
        }
        if (!password.equals(confirm)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        // --- Community guidelines agreed ---
        if (!cbTerms.isChecked()) {
            Toast.makeText(this,
                "Please agree to report honestly and follow community guidelines",
                Toast.LENGTH_SHORT).show();
            return;
        }

        // Save credentials so login works immediately
        LoginActivity.registeredEmail    = email;
        LoginActivity.registeredPassword = password;
        LoginActivity.registeredName     = email.split("@")[0]; // use email prefix as display name

        Toast.makeText(this, "Account created! Please log in.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private boolean isStrongPassword(String pw) {
        if (pw.length() < 8) return false;
        if (!pw.matches(".*[A-Z].*")) return false;
        if (!pw.matches(".*[a-z].*")) return false;
        if (!pw.matches(".*[0-9].*")) return false;
        if (!pw.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}|;':\",./<>?].*")) return false;
        return true;
    }
}