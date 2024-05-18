package com.example.rbc;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.example.client.ApiServices.ApiUserServices;

import org.example.client.dto.UserDTO;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rbc.preference.UserDataManager;
import com.example.rbc.switching_styles.WarningStyle;
import com.example.rbc.warning.ShowWarningLabel;


public class LoginActivity extends AppCompatActivity {

    private EditText loginField;
    private EditText passwordField;

    private TextView registration_link;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginField = findViewById(R.id.loginActivityloginField);
        passwordField = findViewById(R.id.loginActivityPasswordField);
        registration_link = findViewById(R.id.registrationAccountLink);
        loginButtonListener();
        registrationLinkListener();
    }
    private void mainActivityStart(UserDTO user){
        new UserDataManager(getBaseContext()).firstLoginSetup(user);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
    private void loginButtonListener(){

        findViewById(R.id.loginButton).setOnClickListener(v -> {
            String login = loginField.getText().toString();
            String password = passwordField.getText().toString();

            if(password.isEmpty() || login.isEmpty()) {
                if (login.isEmpty()) {
                    WarningStyle.enable(getBaseContext(), loginField);
                    ShowWarningLabel.disable(findViewById(R.id.warning_user_not_found));
                }
                if (password.isEmpty()) {
                    WarningStyle.enable(getBaseContext(), passwordField);
                    ShowWarningLabel.disable(findViewById(R.id.warning_incorrect_password));
                }
            } else {
                WarningStyle.disable(loginField, R.string.login_activity_login_hint_text);
                WarningStyle.disable(passwordField, R.string.login_activity_password_hint_text);
                new Authentification().execute(
                        loginField.getText().toString(),
                        passwordField.getText().toString()
                );
            }
        });
    }

    private void registrationLinkListener(){
        registration_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private class Authentification extends AsyncTask<String, Void, UserDTO> {
        private String errorMessage;

        @Override
        protected UserDTO doInBackground(String... userData) {
            UserDTO user;
            try {
                user = new ApiUserServices().authenticationUser(userData[0], userData[1]);
            } catch (Exception error) {
                errorMessage = error.getMessage();
                return null;
            }
            return user;
        }

        @Override
        protected void onPostExecute(UserDTO user) {
            super.onPostExecute(user);
            ShowWarningLabel.disable(findViewById(R.id.warning_user_not_found));
            ShowWarningLabel.disable(findViewById(R.id.warning_incorrect_password));
            if (user != null) {
                mainActivityStart(user);
            } else{
                if (errorMessage != null){
                    if(errorMessage.equals("User not found!")){
                        ShowWarningLabel.enable(findViewById(R.id.warning_user_not_found));
                    }
                    if(errorMessage.equals("Invalid password!")){
                        ShowWarningLabel.enable(findViewById(R.id.warning_incorrect_password));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Unknown error", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}