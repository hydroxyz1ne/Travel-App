package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private TextInputEditText emailEt, passwordEt;
    private Button loginBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mAuth = FirebaseAuth.getInstance();

        TextInputLayout emailInputLayout = findViewById(R.id.emailEt);
        TextInputLayout passwordInputLayout = findViewById(R.id.passwordEt);

        emailEt = emailInputLayout.findViewById(R.id.editTextLogin);
        passwordEt = passwordInputLayout.findViewById(R.id.editTextPassword);

        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    emailInputLayout.setError("Введите адрес электронной почты");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordInputLayout.setError("Введите пароль");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Авторизация успешна, переходим на следующий экран или выполняем нужные действия
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Login.this, "Авторизация успешна.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, Splash.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Авторизация не удалась
                                    Toast.makeText(Login.this, "Авторизация не удалась. Пожалуйста, проверьте ваш email и пароль.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        TextView textGoToRegister = findViewById(R.id.goToRegisterActivity);
        textGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
