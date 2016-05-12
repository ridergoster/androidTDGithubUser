package com.esgi.vincentk.githubretrofitproject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
/**
 * Created by vincentk on 10/05/2016.
 */
public interface GithubService {
    @GET("/users/{user}")
    Call<GithubUser> gitUser(@Path("user") String user);
}
