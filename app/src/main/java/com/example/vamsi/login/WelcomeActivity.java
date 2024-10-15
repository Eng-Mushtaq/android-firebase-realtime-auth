package com.example.vamsi.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private TextView tvWelcomeMessage, tvName, tvPhone, tvEmail;
    private Button btnSeeInfo;

    private String userId; // To store userId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sucess);

        tvWelcomeMessage = findViewById(R.id.tvWelcome);

        btnSeeInfo = findViewById(R.id.btnSeeInfo);

        // Retrieve data from Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userId = bundle.getString("userId", "N/A");
//            String name = bundle.getString("name", "N/A");
//            String phone = bundle.getString("phone", "N/A");
//            String email = bundle.getString("email", "N/A");

            tvWelcomeMessage.setText("You are now logged in");
//            tvName.setText("Name: " + name);
//            tvPhone.setText("Phone: " + phone);
//            tvEmail.setText("Email: " + email);
        }

        btnSeeInfo.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, UserInfoActivity.class);
            Bundle bundleInfo = new Bundle();
            bundleInfo.putString("userId", userId); // Pass userId to UserInfoActivity
            intent.putExtras(bundleInfo);
            startActivity(intent);
        });
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu); // Inflate your menu resource
        return true;
    }

    // Handle menu item clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_preferred_products) {
            Intent intent = new Intent(WelcomeActivity.this, ListProducts.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
