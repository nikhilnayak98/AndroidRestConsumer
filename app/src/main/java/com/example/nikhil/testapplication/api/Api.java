package com.example.nikhil.testapplication.api;

import android.util.Log;

import com.example.nikhil.testapplication.model.User;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Formatter;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Api {

    private static final String URL = "http://10.0.2.2:8080";
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public Api() {    }

    public static OkHttpClient getHttpClient() {
        return okHttpClient;
    }

    public String getLogin(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        OkHttpClient okHttpClient = getHttpClient();
        String baseUrl = URL;
        MediaType json = MediaType.parse("application/json");

        String loginRequestBody = new Formatter()
                .format("{\"username\":\"%s\",\"password\":\"%s\"}",
                        username, password).toString();
        Request loginRequest = new Request.Builder()
                .url(baseUrl + "/auth")
                .post(RequestBody.create(json, loginRequestBody))
                .build();

        try {
            String loginContent = okHttpClient.newCall(loginRequest)
                    .execute().body().string();

            Log.v("Login Response ", loginContent);
            JsonObject jsonObject = new Gson().fromJson(loginContent, JsonObject.class);
            String accessToken = jsonObject.getAsJsonPrimitive("access_token").toString();
            Log.v("Access Token", accessToken);
            user.setJwtToken(accessToken);
            if(accessToken == null)
                return "Error";
            else
                return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getRegister(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        OkHttpClient okHttpClient = getHttpClient();
        String baseUrl = URL;
        MediaType json = MediaType.parse("application/json");

        String registerRequestBody = new Formatter()
                .format("{\"username\":\"%s\",\"password\":\"%s\"}",
                        username, password).toString();
        Request loginRequest = new Request.Builder()
                .url(baseUrl + "/register")
                .post(RequestBody.create(json, registerRequestBody))
                .build();

        try {
            String registerContent = okHttpClient.newCall(loginRequest)
                    .execute().body().string();

            Log.v("Register Reposnse", registerContent);
            JsonObject jsonObject = new Gson().fromJson(registerContent, JsonObject.class);
            String message = jsonObject.getAsJsonPrimitive("message").toString();
            if(message == null)
                return "Error";
            else
                return message;
        } catch (Exception e) {
            return "";
        }
    }

    public String getAllItems(User user) {
        String accessToken = "JWT " + user.getJwtToken();
        OkHttpClient okHttpClient = getHttpClient();
        String baseUrl = URL;
        MediaType json = MediaType.parse("application/json");

        Request request = new Request.Builder()
                .url(baseUrl + "/items")
                .header("Authorization", accessToken)
                .build();

        try {
            String items = okHttpClient.newCall(request)
                    .execute().body().string();

            Log.v("Items Response", items);
            return items;
        } catch (Exception e) {
            return "";
        }
    }

}
