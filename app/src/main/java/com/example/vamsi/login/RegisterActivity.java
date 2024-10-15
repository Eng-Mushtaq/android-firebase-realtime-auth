package com.example.vamsi.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn, gotoLoginBtn;
    private EditText regName, regPhone, regGmail, regPassword;

    // Firebase Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        registerBtn = findViewById(R.id.btnRegLogin);
        gotoLoginBtn = findViewById(R.id.btnGotoLogin);
        regName = findViewById(R.id.etRegName);
        regPhone = findViewById(R.id.etRegPhone);
        regGmail = findViewById(R.id.etRegGmail);
        regPassword = findViewById(R.id.etRegPassword);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = regName.getText().toString().trim();
                String phone = regPhone.getText().toString().trim();
                String email = regGmail.getText().toString().trim();
                String password = regPassword.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password)) {
                    // Validation messages are handled within isValidPassword
                } else {
                    registerUser(name, phone, email, password);
                }
            }
        });

        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            Toast.makeText(this, "Password must contain at least one uppercase letter", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            Toast.makeText(this, "Password must contain at least one lowercase letter", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            Toast.makeText(this, "Password must contain at least one number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            Toast.makeText(this, "Password must contain at least one special character", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registerUser(String name, String phone, String email, String password) {
        // Generate a unique key for each user
        String userId = databaseReference.push().getKey();

        // Create a HashMap to store user data
        Map<String, String> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("phone", phone);
        userData.put("email", email);
        userData.put("password", password); // Note: Storing plain text passwords is insecure. Consider using hashing.

        // Save user data to Firebase
        assert userId != null;
        databaseReference.child(userId).setValue(userData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}















//
//package com.example.vamsi.login;
//
//import android.content.ContentValues;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import Computing.R;
//
//
//public class RegisterActivity extends AppCompatActivity {
//    private Button registerBtn,gotoLoginBtn;
//
//    private SQLiteOpenHelper openHelper;
//    private SQLiteDatabase db;
//    private EditText regName,regPhone,regGmail,regPassword;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        openHelper = new DatabaseHelper(this);
//
//        registerBtn = findViewById(R.id.btnRegLogin);
//        gotoLoginBtn = findViewById(R.id.btnGotoLogin);
//        regName = findViewById(R.id.etRegName);
//        regPhone = findViewById(R.id.etRegPhone);
//        regGmail = findViewById(R.id.etRegGmail);
//        regPassword = findViewById(R.id.etRegPassword);
//
//        registerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db = openHelper.getWritableDatabase();
//                String fname = regName.getText().toString().trim();
//                String fPhone = regPhone.getText().toString().trim();
//                String fGmail = regGmail.getText().toString().trim();
//                String fPassword = regPassword.getText().toString().trim();
//                if (fname.isEmpty() || fPassword.isEmpty() || fGmail.isEmpty() || fPhone.isEmpty()) {
//                    Toast.makeText(RegisterActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
//                } else {
//                insertData(fname,fPhone,fGmail,fPassword);
//                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//            }
//            }
//        });
//
//        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
//                finish();
//            }
//        });
//    }
//    public void insertData(String fname,String fPhone,String fGmail,String fPassword){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.COL_2,fname);
//        contentValues.put(DatabaseHelper.COL_3,fPhone);
//        contentValues.put(DatabaseHelper.COL_4,fGmail);
//        contentValues.put(DatabaseHelper.COL_5,fPassword);
//
//        long id = db.insert(DatabaseHelper.TABLE_NAME,null,contentValues);
//    }
//}
