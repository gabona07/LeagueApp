package com.example.leagueapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.leagueapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.champion_app_bar, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
//        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
//    }
//
//    private void toolbarInit() {
//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
//        if (navHostFragment != null) {
//            MaterialToolbar toolbar = findViewById(R.id.topAppBar);
//            NavController navController = navHostFragment.getNavController();
//            appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//            setSupportActionBar(toolbar);
//            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        }
//    }
}