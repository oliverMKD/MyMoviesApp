package com.oliver.mymovies.sharedPrefferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.oliver.mymovies.klasi.Token;
import com.oliver.mymovies.klasi.User;

/**
 * Created by Oliver on 2/6/2018.
 */

public class SharedPrefferences {


        private static final String USERID = "USERID";

        private static final String SESIONID = "sesionID";

        private static SharedPreferences getPreferences (Context c) {

            return c.getApplicationContext().getSharedPreferences("MySharedPreffsFile", Activity.MODE_PRIVATE);

        }

        public static void  addUserID (String Email, Context c) {


            getPreferences(c).edit().putString("UserLogin",Email).apply();

        }

        public static Token getToken (Context c) {

            return  new Gson().fromJson(getPreferences(c).getString("token", "" ),Token.class);
        }

        public static String getUserid(Token token, Context c) {

            return getPreferences(c).getString( "UserLogin","");
        }

        public static void addSessionID (String Email, Context c)  {

        getPreferences(c).edit().putString("SessionID", Email).apply();
    }

    public static  String getSessionID (Context c) {

        return getPreferences(c).getString("SessionID", "");
    }
    public static void addRated (String rated, Context c)  {

        getPreferences(c).edit().putString("rated", rated).apply();
    }

    public static  String getRated (Context c) {

        return getPreferences(c).getString("rated", "");
    }
}
