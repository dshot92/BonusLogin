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
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.Objects;

public class ModifyPassword extends AppCompatActivity {

    TextView username, oldPass;
    TextInputLayout newpass, newpassconf;
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

                    user.setPassword(Objects.requireNonNull(newpass.getEditText()).getText().toString());
                    UserFactory.getInstance().modifyPass(user);
                    Intent home = new Intent(ModifyPassword.this, ModifyPassword.class);
                    home.putExtra(EXTRA_USER, user);

                    Context context = getApplicationContext();
                    CharSequence text = "Password Updated";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

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
    }

    private boolean checkInput() {

        //how many error occurred? We need to save the number
        int errors = 0;

        if((Objects.requireNonNull(newpass.getEditText()).getText() == null) || (newpass.getEditText().getText().length() == 0)){
            newpass.setError("Insert Password");
            errors++;
        }else{
            newpass.setError(null);
        }

        if((Objects.requireNonNull(newpassconf.getEditText()).getText() == null) || (newpassconf.getEditText().getText().length() == 0)){
            newpassconf.setError("Insert matching Password");
            errors++;
        }else{
            newpassconf.setError(null);
        }

        if(newpass.getEditText().getText().toString().equals(newpassconf.getEditText().getText().toString())){
            newpassconf.setError(null);
        }else{
            newpassconf.setError("Passowords does not Match");
            errors++;
            //hello
        }

        if(newpass.getEditText().getText().toString().equals(oldPass.getText().toString())){
            newpass.setError("Cannot be equal to actual Password");
            errors++;
        }

        return (errors == 0);
    }
}
