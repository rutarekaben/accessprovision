package rw.iraguha.secureaccess.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseUser;

import rw.iraguha.secureaccess.R;
import rw.iraguha.secureaccess.utils.ImageHolder;


public class Home extends AppCompatActivity {

    public Toolbar toolbar;
    public FirebaseUser mUser;
    public Bitmap mPhoto;
    public Boolean isAvailable;
    public CardView addEmployee,manageEmployee,reports,paradereports;
    public LinearLayout firstStage,secondStage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        addEmployee = findViewById(R.id.addEmployee);
        manageEmployee = findViewById(R.id.manageEmployee);
        reports = findViewById(R.id.reports);
        paradereports = findViewById(R.id.paradereports);

        addEmployee.setOnClickListener(v-> startActivity(new Intent(this,RegisterActivity.class)));
        manageEmployee.setOnClickListener(v-> startActivity(new Intent(this,RegisterActivity.class)));
        reports.setOnClickListener(v-> startActivity(new Intent(this,OtherReportActivity.class)));
        paradereports.setOnClickListener(v-> startActivity(new Intent(this,ReportActivity.class)));

    }
}
