package com.example.cyildirim.espressodemo.utils;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClient {

    private static final String RANDOM_USER_URL = "https://randomuser.me/api/";
    private final OkHttpClient mClient;
    private final JsonParser mJsonParser;
    private static final int SOCKET_TIMEOUT = 30;


    public HttpClient() {
        this.mClient = new OkHttpClient.Builder().readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS).build();
        this.mJsonParser = new JsonParser();
    }


    public boolean fetchUser(String mEmail,String mPassword) throws IOException {
        String url = RANDOM_USER_URL + "?seed=2009dcbeacd33c94";
        Request request = new Request.Builder().url(url).build();
        Response response= mClient.newCall(request).execute();
        JsonObject userObject= mJsonParser.parse(response.body().string()).getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject();
        String email = userObject.get("email").getAsString();
        String password = userObject.get("login").getAsJsonObject().get("password").getAsString();

        if ((email.equals(mEmail)) && (password.equals(mPassword))){
            Log.d("login","Oh here you are ^_^");
            return true;
        }else{
            Log.d("login","one of your credentials are wrong but can't say which one :/ ");
            return false;
        }

    }


    public abstract static class UserCallback implements Runnable {

        protected String cbEmail;
        protected String cbPassword;

    }


}
