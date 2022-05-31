package com.sharkBytesLab.oneoone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sharkBytesLab.oneoone.Models.User;
import com.sharkBytesLab.oneoone.Screens.ConnectingActivity;
import com.sharkBytesLab.oneoone.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private long coins = 0;
    private String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
    private int requestCode = 1;
    private  User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Profiles").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mUser = snapshot.getValue(User.class);
                coins = mUser.getCoins();
                binding.coins.setText("You have : " + coins);

                Glide.with(MainActivity.this).load(mUser.getProfile()).thumbnail(Glide.with(MainActivity.this).load(R.drawable.spinner)).into(binding.circularImageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.findbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isPermissionGranted())
                {

                if(coins > 5)
                {

                    Intent intent = new Intent(MainActivity.this, ConnectingActivity.class);
                    intent.putExtra("profile",mUser.getProfile());
                    startActivity(intent);

                }
                else
                {

                    Toast.makeText(MainActivity.this, "Insufficient Coins...", Toast.LENGTH_SHORT).show();

                }
                }
                else
                {
                    askPermissions();
                }

            }
        });



    }

    private void askPermissions()
    {

        ActivityCompat.requestPermissions(this, permissions, requestCode);

    }

    private boolean isPermissionGranted()
    {
        for(String permission : permissions)
        {
            if(ActivityCompat.checkSelfPermission(this, permission )!= PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }
        }

        return true;
    }

}