package com.example.mytravelbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mytravelbuddy.databinding.ActivitySignUpBinding;
import com.example.mytravelbuddy.util.EmailUtil;
import com.example.mytravelbuddy.util.OTPUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String generatedOtp;
    private String userName;
    private String userEmail;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.SignUpButton.setOnClickListener(v -> {
            userName = binding.name.getEditText().getText().toString().trim();
            userEmail = binding.email.getEditText().getText().toString().trim();
            userPassword = binding.password.getEditText().getText().toString().trim();

            if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                generatedOtp = OTPUtils.generateOTP();
                System.out.println("otp: "+generatedOtp);
                sendEmailOTP(userEmail, generatedOtp);
                Intent intent = new Intent(SignUpActivity.this, OTPVerificationActivity.class);
                intent.putExtra("otp", generatedOtp);
                intent.putExtra("name", userName);
                intent.putExtra("email", userEmail);
                intent.putExtra("password", userPassword);
                startActivity(intent);
            }
        });

        binding.textView.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void sendEmailOTP(String userEmail, String otp) {
        String subject = "Your OTP Verification Code";
        String body = "Dear user,\n\nYour OTP code is: " + otp + "\n\nThank you for signing up.";
        EmailUtil.sendEmail(userEmail, subject, body);
    }
}
