package com.esgi.vincentk.githubretrofitproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vincentk on 10/05/2016.
 */
public class UserActivity extends Activity {
    public static final String USER_NAME = "userName";
    public static final String TAG = "MainActivity";

    @Bind(R.id.username)
    TextView usernameTextView;
    @Bind(R.id.id)
    TextView idTextView;
    @Bind(R.id.email)
    TextView emailTextView;
    @Bind(R.id.avatar)
    ImageView avatarImageView;
    @Bind(R.id.name)
    TextView nameTextView;
    @Bind(R.id.type)
    TextView typeTextView;
    @Bind(R.id.company)
    TextView companyTextView;
    @Bind(R.id.location)
    TextView locationTextView;
    @Bind(R.id.publicRepos)
    TextView publicReposTextView;
    @Bind(R.id.followers)
    TextView followersTextView;
    @Bind(R.id.following)
    TextView followingTextView;
    @Bind(R.id.createdAt)
    TextView createdAtTextView;



    public void setUserView(GithubUser user) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String avatarUrl = user.getAvatarUrl();
        if(user.getGravatarUrl() != null && user.getGravatarUrl().length() > 0) {
            avatarUrl = user.getGravatarUrl();
        }
        Glide.with(avatarImageView.getContext())
                .load(avatarUrl)
                .into(avatarImageView);
        usernameTextView.setText(user.getLogin());
        idTextView.setText(String.valueOf(user.getId()));
        emailTextView.setText(user.getEmail());
        nameTextView.setText(user.getName());
        typeTextView.setText(user.getType());
        companyTextView.setText(user.getCompany());
        locationTextView.setText(user.getLocation());
        publicReposTextView.setText(String.valueOf(user.getPublicRepos()));
        followersTextView.setText(String.valueOf(user.getFollowers()));
        followingTextView.setText(String.valueOf(user.getFollowing()));
        createdAtTextView.setText(df.format(user.getCreatedAt()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        String userName = getIntent().getStringExtra(USER_NAME);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GithubService service = retrofit.create(GithubService.class);
        Call<GithubUser> user = service.gitUser(userName);
        user.enqueue(new Callback<GithubUser>() {
            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                if(response.isSuccessful()) {
                    setUserView(response.body());
                    System.out.println("success");
                }
            }
            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {
                Toast errToast = Toast.makeText(UserActivity.this,"Error...",Toast.LENGTH_SHORT);
                errToast.show();
            }
        });
        Log.d(TAG, "mon ecran est créée");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "mon ecran est plus visible");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "mon ecran devient visible");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "mon ecran démarre");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "mon activité est arrêté");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "mon activité est supprimé");
    }
}