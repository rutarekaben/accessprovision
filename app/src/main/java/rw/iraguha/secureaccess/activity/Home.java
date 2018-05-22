package rw.iraguha.secureaccess.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseUser;

import rw.iraguha.secureaccess.R;
import rw.iraguha.secureaccess.utils.ImageHolder;


public class Home extends AppCompatActivity {

    public Toolbar toolbar;
    public FirebaseUser mUser;
    public Bitmap mPhoto;
    public Boolean isAvailable;
    public ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mImageView = findViewById(R.id.mImageView);
        if(getIntent() != null){
            isAvailable = getIntent().getBooleanExtra("photo", false);
            if(isAvailable){
                mPhoto = ImageHolder.get().getLargeData();
                mImageView.setImageBitmap(mPhoto);
            }
        }
        BottomNavigationView b_nav = findViewById(R.id.navigation);
        Helper.disableShiftMode(b_nav);
        b_nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.home_about:
                    //
                    break;
                case R.id.menu_student:
                    //
                    break;
                case R.id.menu_profile:
                    Intent mAIntent = new Intent(Home.this,LoginActivity.class);
                    startActivity(mAIntent);
                    break;
                default:
                    //;
            }
            return true;
        });
    }
}
