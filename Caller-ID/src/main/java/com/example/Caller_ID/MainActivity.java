package com.example.Caller_ID;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;

    FireBaseWorker fireBaseWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mDatabaseHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_call_log,
                R.id.navigation_check_number, R.id.navigation_spam_protection, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        //uncommeny this to create local database and add record
        //String testPhone="+7-495-999-99-99";
        //Boolean isSpam=false;
        //mDatabaseHelper.addRecord(testPhone, isSpam);
        fireBaseWorker = new FireBaseWorker(getApplicationContext());

        fireBaseWorker.download();
    }


}
