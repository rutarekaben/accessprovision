package rw.iraguha.secureaccess.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rw.iraguha.secureaccess.SharedPreference;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreference mSharedPreference =  new SharedPreference(this);
        sharedPref = mSharedPreference.getIntance();
        boolean  first      = sharedPref.getBoolean("first",true);
        boolean logged_in   = sharedPref.getBoolean("logged_in", false);
        if(first){
            startActivity(new Intent(getApplicationContext(), OnBoarding.class));
            finish();
        }else if(logged_in){
            // Start Main activity //change it temps
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
        }else {
            // Start Login activity
            //TODO: i will change it later back to LoginActivity
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }

}
