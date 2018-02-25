package com.oliver.mymovies.interceptor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.util.Log;

import com.oliver.mymovies.LogIn;
import com.oliver.mymovies.api.ApiConstants;
import com.oliver.mymovies.klasi.Token;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.sharedPrefferences.SharedPrefferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by Oliver on 1/18/2018.
 */

public class LoggingInterceptor implements Interceptor {
    private Context activity;
    private Token token;
    private boolean logout = false;
    User user;
    public LoggingInterceptor(Context activity, Token token) {
        this.activity = activity;
        this.token = token;
    }
    @SuppressLint("WrongConstant")
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        String access_token = null;
        try {
            access_token = token.request_token;
        } catch (NullPointerException e) {
        }
        request = request.newBuilder().addHeader("Authorization", "Bearer " + ((access_token != null) ? access_token : "")).build();
        long t1 = System.nanoTime();
        if ((request.body() != null && request.body().contentLength() < 2048) || request.body() == null)
            Log.d("Retrofit", String.format("Sending request %s on %s%n%s", request.url(), chain
                    .connection(), request.headers()) + " Params " + bodyToString(request));
        Response response = chain.proceed(request);
        String responseBodyString = response.body().string();
        long t2 = System.nanoTime();
        Log.d("Retrofit", String.format("Received response for %s in %.1fms%n%s", response.request
                ().url(), (t2 - t1) / 1e6d, response.headers()) + "Response code:" + response.code() + "\n" + "Body " + responseBodyString);
        if ((response.code() == 403 || response.code() == 401) && !(activity instanceof LogIn) && !response.request().url().toString().contains(ApiConstants.USER_DETAILS) && !logout) {
//            String user = SharedPrefferences.getInstance(activity).getUsername();
            JSONObject obj = null;
            String message = "";
            try {
                obj = new JSONObject(responseBodyString);
                if (obj.has("message"))
                    message = obj.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            logout = true;
            Intent signIn = new Intent(activity, LogIn.class).putExtra("username",user ).putExtra("message", message);
            signIn.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(signIn);
            ((Activity) activity).finish();
            response.close();
        }
        return response.newBuilder().body(ResponseBody.create(response.body().contentType(),
                responseBodyString)).build();
    }
    private String bodyToString(final Request request) {
        try {
            final Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        } catch (NullPointerException e) {
            return "did not work nullPointer";
        } catch (OutOfMemoryError e) {
            return "OutOfMemoryError ";
        }
    }
}