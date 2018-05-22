package rw.iraguha.secureaccess.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

import rw.iraguha.secureaccess.R;
import rw.iraguha.secureaccess.SharedPreference;

public class OnBoarding extends AhoyOnboarderActivity {
    private static final String TAG = "OnBoarding";
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreference mSharedPreference =  new SharedPreference(this);
        sharedPref = mSharedPreference.getIntance();
        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Access Provisioning", "Access you data any time Any where securely.", R.drawable.ic_shield);
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Two Factor Authentication", "We use 2 factor authentication to increase security of data ", R.drawable.ic_lock);
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Face Recognition", "We Use face recognition to verify if it you.", R.drawable.ic_shield_out);

        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.white);
            page.setDescriptionColor(R.color.grey_200);
            //page.setTitleTextSize(dpToPixels(12, this));
            //page.setDescriptionTextSize(dpToPixels(8, this));
            //page.setIconLayoutParams(width, height, marginTop, marginLeft, marginRight, marginBottom);
        }

        setFinishButtonTitle("Finish");
        showNavigationControls(true);
        setGradientBackground();

        //set the button style you created
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));
        }

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        setFont(face);
        setOnboardPages(pages);

    }

    @Override
    public void onFinishButtonPressed() {
        editor = sharedPref.edit();
        editor.putBoolean("first", false);
        editor.apply();
        //TODO: i will change back later to LoginActivity
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

}
