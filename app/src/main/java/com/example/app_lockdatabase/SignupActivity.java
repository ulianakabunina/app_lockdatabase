package com.example.app_lockdatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnSignup;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper = new DatabaseHelper(this);

        etUsername = findViewById(R.id.etSignupUsername);
        etPassword = findViewById(R.id.etSignupPassword);
        btnSignup = findViewById(R.id.btnDoSignup);

        btnSignup.setOnClickListener(v -> {
            String u = etUsername.getText().toString().trim();
            String p = etPassword.getText().toString();

            if (u.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show();
                return;
            }

            long id = dbHelper.addUser(u, p);

            if (id != -1) {
                Toast.makeText(this, "Регистрация успешна! ID = " + id, Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Логин уже занят", Toast.LENGTH_LONG).show();
            }
        });
    }
}
