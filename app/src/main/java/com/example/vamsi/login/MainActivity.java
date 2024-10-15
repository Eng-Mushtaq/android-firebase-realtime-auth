package com.example.vamsi.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText etLoginGmail, etLoginPassword;
    private Button loginButton;
    private int loginAttempts = 0;
    private static final int MAX_ATTEMPTS = 2;

    // Firebase Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        TextView tvRegister = findViewById(R.id.tvRegister);
        etLoginGmail = findViewById(R.id.etLogGmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check if login is disabled
                if (loginButton.isEnabled()) {
                    String email = etLoginGmail.getText().toString().trim();
                    String password = etLoginPassword.getText().toString().trim();

                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Enter your Email and Password to login", Toast.LENGTH_SHORT).show();
                    } else {
                        authenticateUser(email, password);
                    }
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Navigate to RegisterActivity
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void authenticateUser(String email, String password) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isUserFound = false;
                String userId = null;

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String dbEmail = userSnapshot.child("email").getValue(String.class);
                    String dbPassword = userSnapshot.child("password").getValue(String.class);

                    if (dbEmail != null && dbEmail.equals(email) && dbPassword != null && dbPassword.equals(password)) {
                        isUserFound = true;
                        userId = userSnapshot.getKey();
                        break;
                    }
                }

                if (isUserFound && userId != null) {
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    // Retrieve user information
                    String name = snapshot.child(userId).child("name").getValue(String.class);
                    String phone = snapshot.child(userId).child("phone").getValue(String.class);
                    String userEmail = snapshot.child(userId).child("email").getValue(String.class);

                    // Pass data using Bundle
                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", userId); // Pass userId
                    bundle.putString("name", name);
                    bundle.putString("phone", phone);
                    bundle.putString("email", userEmail);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else {
                    loginAttempts++;
                    if (loginAttempts >= MAX_ATTEMPTS) {
                        Toast.makeText(MainActivity.this, "Login is disabled", Toast.LENGTH_SHORT).show();
                        loginButton.setEnabled(false);
                    } else {
                        Toast.makeText(MainActivity.this, "Login error: Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
