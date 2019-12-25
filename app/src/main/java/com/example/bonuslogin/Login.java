/*
    Corso IUM - AA 2019 - 2020
    65577 - Daniele Stochino
    Esercitazione Bonus
 */

package com.example.bonuslogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;

public class Login extends AppCompatActivity {

    List<User> userList;
    User user;
    EditText username, password;
    Button signIn_button;
    TextView signup_text;

    public static final String EXTRA_USER = "package com.example.BonusLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User();
        username = findViewById(R.id.input_login_username);
        password = findViewById(R.id.input_login_password);
        signIn_button = findViewById(R.id.signin_button);
        signup_text = findViewById(R.id.signup_text);


        signIn_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInput()) {
                Intent home = new Intent(Login.this, Home.class);
                home.putExtra(EXTRA_USER, user);
                startActivity(home);
            }
         }
        });

        signup_text.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent showResult = new Intent(Login.this, SignUp.class);
            showResult.putExtra(EXTRA_USER, user);
            startActivity(showResult);
            }
        });
    }

    private boolean checkInput() {

        //how many error occurred? We need to save the number
        userList = UserFactory.getInstance().getUsers();
        int errors = 0;

        if(username.getText() == null || username.getText().length() == 0){  //the second condition is for the case where the user wrote and then erased all
            username.setError("Insert Username");
            errors++;
        }else{
            username.setError(null);
        }
        if(password.getText() == null || password.getText().length() == 0){  //the second condition is for the case where the user wrote and then erased all
            password.setError("Insert Password");
            errors++;
        }else{
            password.setError(null);
        }

        errors++;
        username.setError("Wrong Username or Wrong Password");
        password.setError("Wrong Password or Wrong Password");
        for (User u : userList) {
            if (u.getPassword().equals(password.getText().toString()) && u.getUsername().equals(username.getText().toString())) {
                user = u;
                errors = 0;
                username.setError(null);
                password.setError(null);
            }
        }

        return (errors == 0);
    }
}
