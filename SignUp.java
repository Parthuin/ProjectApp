package com.example.blalonde9489.projectapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences my_preferences;
    private User user;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = AppDatabase.getDatabase(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnSignUp) {
            // TODO add trophy
            // TODO call updatefirstUserData
            TextView name=(TextView) findViewById(R.id.txtName);
            TextView email=(TextView) findViewById(R.id.txtEmail);
            TextView password=(TextView) findViewById(R.id.txtPass);
            //for(int i=0;i<database.userDao().getAllUser().size();i++)
            database.userDao().addUser(new User(1, name.toString(), email.toString(), password.toString()));



        }

    }
}
