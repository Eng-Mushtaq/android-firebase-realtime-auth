package com.example.vamsi.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

public class UserInfoActivity extends AppCompatActivity {

    private TextView tvUserName, tvUserEmail;
    private EditText etUserPhone;
    private Button btnEdit, btnSave, btnDeleteAccount;

    private String userId;
    private String currentPhone;

    // Firebase Database reference
    private DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Initialize Views
        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        etUserPhone = findViewById(R.id.etUserPhone);
        btnEdit = findViewById(R.id.btnEdit);
        btnSave = findViewById(R.id.btnSave);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        // Initially, disable editing
        etUserPhone.setEnabled(false);
        etUserPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        etUserPhone.setFocusable(false);
        etUserPhone.setCursorVisible(false);

        // Get the userId from intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userId = bundle.getString("userId", "N/A");
        }

        if (userId.equals("N/A")) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize Firebase reference
        userReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        // Fetch and display user information
        fetchUserInfo();

        // Set OnClickListener for Edit Button
        btnEdit.setOnClickListener(v -> {
            etUserPhone.setEnabled(true);
            etUserPhone.setFocusableInTouchMode(true);
            etUserPhone.setCursorVisible(true);
            btnSave.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.GONE);
        });

        // Set OnClickListener for Save Button
        btnSave.setOnClickListener(v -> {
            String updatedPhone = etUserPhone.getText().toString().trim();
            if (updatedPhone.isEmpty()) {
                Toast.makeText(UserInfoActivity.this, "Phone number cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                updatePhoneNumber(updatedPhone);
            }
        });

        // Set OnClickListener for Delete Account Button
        btnDeleteAccount.setOnClickListener(v -> {
            confirmDeletion();
        });
    }

    /**
     * Fetches the user's information from Firebase and displays it.
     */
    private void fetchUserInfo() {
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue(String.class);
                String phone = snapshot.child("phone").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);

                tvUserName.setText(name != null ? name : "N/A");
                tvUserEmail.setText(email != null ? email : "N/A");
                etUserPhone.setText(phone != null ? phone : "");
                currentPhone = phone != null ? phone : "";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserInfoActivity.this, "Failed to load user info: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Updates the user's phone number in Firebase.
     *
     * @param updatedPhone The new phone number to be saved.
     */
    private void updatePhoneNumber(String updatedPhone) {
        userReference.child("phone").setValue(updatedPhone)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(UserInfoActivity.this, "Phone number updated successfully", Toast.LENGTH_SHORT).show();
                        etUserPhone.setEnabled(false);
                        etUserPhone.setFocusable(false);
                        etUserPhone.setCursorVisible(false);
                        btnSave.setVisibility(View.GONE);
                        btnEdit.setVisibility(View.VISIBLE);
                        currentPhone = updatedPhone;
                    } else {
                        Toast.makeText(UserInfoActivity.this, "Failed to update phone number", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Prompts the user to confirm account deletion.
     */
    private void confirmDeletion() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteAccount())
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Deletes the user's account from Firebase and redirects to the login screen.
     */
    private void deleteAccount() {
        userReference.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(UserInfoActivity.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                        // Navigate back to MainActivity (Login screen)
                        Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Close the current activity
                    } else {
                        Toast.makeText(UserInfoActivity.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
