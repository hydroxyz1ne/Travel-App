package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText emailEt, passwordEt;
    private Button registerBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mAuth = FirebaseAuth.getInstance();

        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    emailEt.setError("Введите адрес электронной почты");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordEt.setError("Введите пароль");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Регистрация успешна, выполняем нужные действия
                                    Toast.makeText(Register.this, "Регистрация успешна.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Регистрация не удалась
                                    Toast.makeText(Register.this, "Регистрация не удалась. Пожалуйста, попробуйте снова.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        TextView textGoToLogin = findViewById(R.id.goToLoginActivity);
        textGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода на активити входа
                Intent intent = new Intent(Register.this, Login.class);
                // Запускаем активити для входа
                startActivity(intent);
            }
        });
    }
}

