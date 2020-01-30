/*
    Corso IUM - AA 2019 - 2020
    65577 - Daniele Stochino
    Esercitazione Bonus
 */

package com.example.bonuslogin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class ModifyPassword extends AppCompatActivity {

    TextView username, oldPass;
    EditText  newpass, newpassconf;
    Button modify, home;
    User user;
    boolean isPasswordVisibleNEW, isPasswordVisibleCONFIRM;

    public static final String EXTRA_USER = "package com.example.BonusLogin";


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        username = findViewById(R.id.attributeUsername);
        oldPass = findViewById(R.id.attributeOldPassword);
        newpass = findViewById(R.id.input_passwordNew);
        newpassconf = findViewById(R.id.input_passwordNewConfirm);
        modify = findViewById(R.id.input_button_signUp);
        home = findViewById(R.id.input_button_home);
        isPasswordVisibleNEW = true;
        isPasswordVisibleCONFIRM = true;

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(Login.EXTRA_USER);

        if(obj instanceof User){
            user = (User) obj;
        }else{
            user = new User();
        }

        username.setText(user.getUsername());
        oldPass.setText(user.getPassword());

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkInput()) {
                        user.setPassword(newpass.getText().toString());
                        UserFactory.getInstance().modifyPass(user);
                        Intent home = new Intent(ModifyPassword.this, ModifyPassword.class);
                        home.putExtra(EXTRA_USER, user);
                        startActivity(home);
                }else{
                    //todo trhow exception for mismathch new passwords
                }
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(ModifyPassword.this, Home.class);
                home.putExtra(EXTRA_USER, user);
                startActivity(home);
            }
        });

        newpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (newpass.getRight() - newpass.getCompoundDrawables()[RIGHT].getBounds().width())) {
                        int selection = newpass.getSelectionEnd();
                        if (isPasswordVisibleNEW) {
                            // set drawable image
                            newpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password2, 0);
                            // hide Password
                            newpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisibleNEW = false;
                        } else  {
                            // set drawable image
                            newpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password2, 0);
                            // show Password
                            newpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisibleNEW = true;
                        }
                        newpass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        newpassconf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (newpassconf.getRight() - newpassconf.getCompoundDrawables()[RIGHT].getBounds().width())) {
                        int selection = newpassconf.getSelectionEnd();
                        if (isPasswordVisibleCONFIRM) {
                            // set drawable image
                            newpassconf.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password2, 0);
                            // hide Password
                            newpassconf.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisibleCONFIRM = false;
                        } else  {
                            // set drawable image
                            newpassconf.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password2, 0);
                            // show Password
                            newpassconf.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisibleCONFIRM = true;
                        }
                        newpassconf.setSelection(selection);
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

        if(newpass.getText() == null || newpass.getText().length() == 0){
            newpass.setError("Insert Password");
            errors++;
        }else{
            newpass.setError(null);
        }

        if(newpassconf.getText() == null || newpassconf.getText().length() == 0 ){
            newpassconf.setError("Insert matching Password");
            errors++;
        }else{
            newpassconf.setError(null);
        }

        if(newpass.getText().toString().equals(newpassconf.getText().toString())){
            newpassconf.setError(null);
        }else{
            newpassconf.setError("Passowords does not Match");
            errors++;
            //hello
        }

        if(newpass.getText().toString().equals(oldPass.getText().toString())){
            newpass.setError("Cannot be equal to actual Password");
            errors++;
        }

        return (errors == 0);
    }
}
