/*
    Corso IUM - AA 2019 - 2020
    65577 - Daniele Stochino
    Esercitazione Bonus
 */

package com.example.bonuslogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Home extends AppCompatActivity {

    User user;
    TextView welcome, username, password, city, datetext, modifyPassword;
    Button logout;

    public static final String EXTRA_USER = "package com.example.BonusLogin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcome = findViewById(R.id.attributeWelcome);
        username = findViewById(R.id.attributeUsername);
        password = findViewById(R.id.attributePassword);
        city = findViewById(R.id.attributeCity);
        datetext = findViewById(R.id.attribute_date);
        datetext.setInputType(InputType.TYPE_NULL);
        modifyPassword= findViewById(R.id.input_modifyPassword);
        logout = findViewById(R.id.input_button_signOut);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(Login.EXTRA_USER);

        if(obj instanceof User){
            user = (User) obj;
        }else{
            user = new User();
        }

        welcome.setText("Welcome " + user.getUsername().toString() + "!");
        username.setText(user.getUsername());
        password.setText(user.getPassword());
        city.setText(user.getCity());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        datetext.setText(format.format(user.getDate().getTime()));

        modifyPassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                Intent modifyPage = new Intent(Home.this, ModifyPassword.class);
                modifyPage.putExtra(EXTRA_USER, user);
                startActivity(modifyPage);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = null;
                Intent login = new Intent(Home.this, Login.class);
                login.putExtra(EXTRA_USER, user);
                startActivity(login);
            }
        });

    }
}
