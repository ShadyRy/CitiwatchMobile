package com.example.citiwatch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GetStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        Button btnGetStarted = findViewById(R.id.btnGetStarted);
        TextView tvAlreadyHaveAccount = findViewById(R.id.tvAlreadyHaveAccount);

        btnGetStarted.setOnClickListener(v ->
            startActivity(new Intent(this, SignUpActivity.class)));

        tvAlreadyHaveAccount.setOnClickListener(v ->
            startActivity(new Intent(this, LoginActivity.class)));
    }
}