package com.esgi.vincentk.githubretrofitproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.username) EditText username;
    @Bind(R.id.userButton) Button userButton;

    public void openUserActivity(GithubUser githubUser) {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra(UserActivity.GITHUB_USER, githubUser);
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
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                if(userName.length() < 1) {
                    Toast.makeText(MainActivity.this,"Username too short...", Toast.LENGTH_SHORT).show();
                } else {
                    final Toast loadToast = Toast.makeText(MainActivity.this,"Loading...",Toast.LENGTH_LONG);
                    loadToast.show();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.github.com")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    GithubService service = retrofit.create(GithubService.class);
                    Call<GithubUser> user = service.gitUser(userName);
                    user.enqueue(new Callback<GithubUser>() {
                        @Override
                        public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                            if (loadToast.getView().isShown()){ loadToast.cancel();}
                            if(response.isSuccessful()) {
                                openUserActivity(response.body());
                            } else {
                                Toast errToast = Toast.makeText(MainActivity.this,response.code() + ": " + response.message(),Toast.LENGTH_SHORT);
                                errToast.show();
                            }
                        }
                        @Override
                        public void onFailure(Call<GithubUser> call, Throwable t) {
                            if (loadToast.getView().isShown()){ loadToast.cancel();}
                            Toast errToast = Toast.makeText(MainActivity.this,"Error...",Toast.LENGTH_SHORT);
                            errToast.show();
                        }
                    });
                }
            }
        });
    }
}
