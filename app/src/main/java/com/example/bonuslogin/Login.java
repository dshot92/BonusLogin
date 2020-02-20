/*
    Corso IUM - AA 2019 - 2020
    65577 - Daniele Stochino
    Esercitazione Bonus
 */

package com.example.bonuslogin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

public class Login extends AppCompatActivity {

    List<User> userList;
    User user;
    TextInputLayout username, password;
    Button signIn_button;
    TextView signup_text;
    boolean isPasswordVisible = true;

    public static final String EXTRA_USER = "package com.example.BonusLogin";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = new User();
        username = findViewById(R.id.input_login_username);
        password = findViewById(R.id.input_login_password);
        signIn_button = findViewById(R.id.signin_button);
        signup_text = findViewById(R.id.signup_text);

        signIn_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInput()) {
                Context context = getApplicationContext();
                CharSequence text = "Welcome Back " + username.getEditText().getText().toString();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
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

        if(username.getEditText().getText().toString().length() == 0){  //the second condition is for the case where the user wrote and then erased all
            username.setError("Insert Username");
            errors++;
        }else{
            username.setError(null);
        }
        if(password.getEditText().getText().toString().length() == 0){  //the second condition is for the case where the user wrote and then erased all
            password.setError("Insert Password");
            errors++;
        }else{
            for (User u : userList) {
                if (u.getUsername().equals(username.getEditText().getText().toString()) && u.getPassword().equals(password.getEditText().getText().toString())){
                    user = u;
                    return true;
                }
            }
            errors++;
            username.setError("Insert Valid Username");
            password.setError("Insert Valid Password");
        }
/*
        errors++;
        // Check with userList in input was not Empty
        for (User u : userList) {
            if ( (u.getUsername().equals(username.getEditText().getText().toString()))){
                username.setError(null);
                // CASE CORRECT user but WRONG password
                if ( (u.getUsername().equals(username.getEditText().getText().toString()))
                        && !(u.getPassword().equals(password.getEditText().getText().toString()))) {
                    errors++;
                    password.setError("Wrong Password");
                }
                // CASE WRONG user but CORRECT password
                if ( !(u.getUsername().equals(username.getEditText().getText().toString()))
                        && (u.getPassword().equals(password.getEditText().getText().toString()))) {
                    errors++;
                    username.setError("Wrong Username");
                }

                // CASE CORRECT user but CORRECT password
                if (u.getPassword().equals(password.getEditText().getText().toString())
                     && u.getPassword().equals(password.getEditText().getText().toString())) {
                    user = u; // USER FOUND
                    username.setError(null);
                    password.setError(null);
                    return true;
                }
            }else{
                errors++;
                username.setError("Wrong Username");
            }
        }

        */
        return (errors == 0);
    }

}
