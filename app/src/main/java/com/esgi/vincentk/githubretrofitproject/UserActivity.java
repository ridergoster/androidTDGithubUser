package com.esgi.vincentk.githubretrofitproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.text.SimpleDateFormat;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vincentk on 10/05/2016.
 */
public class UserActivity extends Activity {
    public static final String GITHUB_USER = "githubUser";
    public static final String TAG = "MainActivity";

    @Bind(R.id.username)
    TextView usernameTextView;
    @Bind(R.id.id)
    TextView idTextView;
    @Bind(R.id.email)
    TextView emailTextView;
    @Bind(R.id.avatar)
    CircleImageView avatarImageView;
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
        Glide.with(avatarImageView.getContext())
                .load(user.getAvatarUrl())
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
        GithubUser githubUser = (GithubUser) getIntent().getParcelableExtra(GITHUB_USER);
        setUserView(githubUser);
    }
}