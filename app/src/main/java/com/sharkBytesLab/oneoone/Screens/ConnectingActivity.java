package com.sharkBytesLab.oneoone.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkBytesLab.oneoone.R;
import com.sharkBytesLab.oneoone.databinding.ActivityConnectingBinding;

public class ConnectingActivity extends AppCompatActivity {

    private ActivityConnectingBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConnectingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}