package com.example.mytravelbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravelbuddy.LoginActivity;
import com.example.mytravelbuddy.Models.User;
import com.example.mytravelbuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class OTPVerificationActivity extends AppCompatActivity {

    private EditText otpInput;
    private String actualOtp, name, email, password;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        otpInput = findViewById(R.id.otp_input);
        Button verifyButton = findViewById(R.id.verify_button);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        actualOtp = getIntent().getStringExtra("otp");
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        verifyButton.setOnClickListener(v -> {
            String enteredOtp = otpInput.getText().toString().trim();
            if (enteredOtp.equals(actualOtp)) {
                // Proceed with registration
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                String uid = mAuth.getCurrentUser().getUid();
                                User user = new User();
                                user.setId(uid);
                                user.setEmail(email);
                                user.setName(name);

                                db.collection("Users").document(uid).set(user)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(this, LoginActivity.class));
                                            finish();
                                        });
                            } else {
                                Toast.makeText(this, "Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
