package com.esgi.vincentk.githubretrofitproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.username) EditText username;
    @Bind(R.id.userButton) Button userButton;
    Gson gson = new Gson();
    public static final String USER_NAME = "userName";

    public void openUserActivity(String username) {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra(UserActivity.USER_NAME, username);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                if(userName.length() < 1) {
                    Toast.makeText(MainActivity.this,"Username too short...", Toast.LENGTH_SHORT).show();
                } else {
                    openUserActivity(userName);
                }
            }
        });
    }
}
