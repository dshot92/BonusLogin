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
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class ModifyPassword extends AppCompatActivity {

    TextView username, oldPass, newpass, newpassconf;
    Button modify, home;
    User user;

    public static final String EXTRA_USER = "package com.example.BonusLogin";


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
        }

        return (errors == 0);
    }
}
