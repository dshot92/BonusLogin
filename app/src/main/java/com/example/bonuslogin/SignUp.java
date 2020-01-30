/*
    Corso IUM - AA 2019 - 2020
    65577 - Daniele Stochino
    Esercitazione Bonus
 */

package com.example.bonuslogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SignUp extends AppCompatActivity {

    User user = new User();
    TextView username, city, date;
    EditText password, passwordConf;
    Button signup_button;
    DatePickerFragment datePickerFragment = new DatePickerFragment();
    boolean isPasswordVisibleNEW, isPasswordVisibleCONFIRM;

    public static final String EXTRA_USER = "package com.example.BonusLogin";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.input_signup_username);
        password = findViewById(R.id.input_signup_password);
        passwordConf = findViewById(R.id.input_signup_passwordConfirm);
        city = findViewById(R.id.input_signup_city);
        date = findViewById(R.id.input_signup_date);
        date.setInputType(InputType.TYPE_NULL);
        signup_button = findViewById(R.id.input_button_signUp);
        isPasswordVisibleNEW = true;
        isPasswordVisibleCONFIRM = true;

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here we want to show our datepickerFragment
                datePickerFragment.show(getSupportFragmentManager(), "date picker");
            }
        });

        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    datePickerFragment.show(getSupportFragmentManager(), "date picker");
                }
            }
        });

        datePickerFragment.setOnDatePickerFragmentChanged(new DatePickerFragment.DatePickerFragmentListener() {
            @Override
            public void onDatePickerFragmentOkButton(DialogFragment dialog, Calendar dateX) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                date.setText(format.format(dateX.getTime()));
            }

            @Override
            public void onDatePickerFragmentCancelButton(DialogFragment dialog) {

            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {

                    user.setUsername(username.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setCity(city.getText().toString());
                    user.setDate(datePickerFragment.getDate());

                    UserFactory.getInstance().addUsers(user);
                    Intent home = new Intent(SignUp.this, Home.class);
                    home.putExtra(EXTRA_USER, user);
                    startActivity(home);
                }else{
                    //TODO raise expection for not matching passwords
                }
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[RIGHT].getBounds().width())) {
                        int selection = password.getSelectionEnd();
                        if (isPasswordVisibleNEW) {
                            // set drawable image
                            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password2, 0);
                            // hide Password
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisibleNEW = false;
                        } else  {
                            // set drawable image
                            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password2, 0);
                            // show Password
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisibleNEW = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        passwordConf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordConf.getRight() - passwordConf.getCompoundDrawables()[RIGHT].getBounds().width())) {
                        int selection = passwordConf.getSelectionEnd();
                        if (isPasswordVisibleCONFIRM) {
                            // set drawable image
                            passwordConf.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password2, 0);
                            // hide Password
                            passwordConf.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisibleCONFIRM = false;
                        } else  {
                            // set drawable image
                            passwordConf.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password2, 0);
                            // show Password
                            passwordConf.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisibleCONFIRM = true;
                        }
                        passwordConf.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private boolean checkInput() {

        //how many error occurred? We need to save the number
        int errors = 0;

        if(username.getText() == null || username.getText().length() == 0){
            username.setError("Insert UserName");
            errors++;
        }else{
            username.setError(null);
        }

        if(password.getText() == null || password.getText().length() == 0){
            password.setError("Insert Password");
            errors++;
        }else{
            password.setError(null);
        }

        if(passwordConf.getText() == null || passwordConf.getText().length() == 0 ){
            passwordConf.setError("Passowrd does not Match");
            errors++;
        }else{
            passwordConf.setError(null);
        }

        if( password.getText().toString().equals(passwordConf.getText().toString())) {
            passwordConf.setError(null);
        }else{
            passwordConf.setError("Password must Match");
            errors++;
        }

        if(city.getText() == null || city.getText().length() == 0 ){
            city.setError("Insert City");
            errors++;
        }else{
            city.setError(null);
        }

        if(date.getText() == null || date.getText().length() == 0){
            date.setError("Insert Birthday");
            errors++;
        }else{
            date.setError(null);
        }

        List<User> userList = UserFactory.getInstance().getUsers();
        for (User u: userList) {
            if ( u.getUsername().equals( username.getText().toString() ) && u.getPassword().equals( password.getText().toString() ) ) {
                errors++;
                username.setError("Username already Exist");
            }
        }

        return (errors == 0);
    }
}
