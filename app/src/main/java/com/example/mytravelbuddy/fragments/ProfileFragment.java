package com.example.mytravelbuddy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytravelbuddy.R;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;

    private TextInputEditText nameEditText, bioEditText, mobileEditText, emailEditText;
    private AutoCompleteTextView  genderDropdown, ageDropdown, professionDropdown, companioGenderDropdown, companioProfessionDropdown;

    private TextView userNameEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        // Initialize Views
        userNameEditText = view.findViewById(R.id.userName);
        nameEditText = view.findViewById(R.id.name);
        bioEditText = view.findViewById(R.id.bio);    // give ID "bio"
       mobileEditText = view.findViewById(R.id.mobile);  // give ID "mobile"
        emailEditText = view.findViewById(R.id.email);    // give ID "email"

        genderDropdown = view.findViewById(R.id.gender);
        ageDropdown = view.findViewById(R.id.age);
        professionDropdown = view.findViewById(R.id.profession);
      //  companioGenderDropdown = view.findViewById(R.id.companioGender);
      //  companioProfessionDropdown = view.findViewById(R.id.companioProfession);

        loadUserProfile();

        return view;
    }

    private void loadUserProfile() {
        db.collection("Users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        documentSnapshot.getString("name");
                        userNameEditText.setText(documentSnapshot.getString("name"));
                        nameEditText.setText(documentSnapshot.getString("name"));
                        //bioEditText.setText(documentSnapshot.getString("bio"));
                        //mobileEditText.setText(documentSnapshot.getString("mobile"));
                        emailEditText.setText(documentSnapshot.getString("email"));
                        //genderDropdown.setText(documentSnapshot.getString("gender"));
                        //ageDropdown.setText(documentSnapshot.getString("age"));
                        //professionDropdown.setText(documentSnapshot.getString("profession"));
                        //companioGenderDropdown.setText(documentSnapshot.getString("companioGender"));
                        //companioProfessionDropdown.setText(documentSnapshot.getString("companioProfession"));
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to load profile", Toast.LENGTH_SHORT).show();
                });
    }
}
