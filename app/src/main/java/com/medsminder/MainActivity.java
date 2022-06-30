package com.medsminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.medsminder.view.AddNewItemFragment;
import com.medsminder.view.DashboardFragment;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "MedsMinder";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment currentFrament = null;
//                switch (item.getItemId()) {
//                    case R.id.navigation_home:
//                        currentFrament = new AddNewItemFragment();
//                        break;
//                    case R.id.navigation_dashboard:
//                        currentFrament = new DashboardFragment();
//                        break;
//                }
//
//                if (currentFrament != null) {
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, currentFrament).commit();
//                }
//                return true;
//            }
//        });


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new DashboardFragment()).commit();


        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton.hide();
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack("dash")
                        .replace(R.id.fragmentContainerView, new AddNewItemFragment())
                        .commit();

            }
        });


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Life Tracker Notification",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}