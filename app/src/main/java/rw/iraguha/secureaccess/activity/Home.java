package rw.iraguha.secureaccess.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import es.dmoral.toasty.Toasty;
import rw.iraguha.secureaccess.R;
import rw.iraguha.secureaccess.model.User;
import rw.iraguha.secureaccess.utils.ImageHolder;


public class Home extends AppCompatActivity {
    private static final String TAG = "Home";
    public Toolbar toolbar;
    public Bitmap mPhoto;
    public Boolean isAvailable;
    public CardView addEmployee,viewreport,reports,paradereports,viewParadeReport;
    public LinearLayout firstStage,secondStage,thirdStage;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        firstStage =  findViewById(R.id.firstStage);
        secondStage =  findViewById(R.id.secondStage);
        thirdStage =  findViewById(R.id.thirdStage);
        addEmployee = findViewById(R.id.addEmployee);
        viewreport = findViewById(R.id.viewreport);
        reports = findViewById(R.id.reports);
        paradereports = findViewById(R.id.paradereports);
        viewParadeReport = findViewById(R.id.viewParadeReport);


        //getting current user
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        db.collection("users")
                .whereEqualTo("phonenumber", mUser.getPhoneNumber().substring(1))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            User mUser = document.toObject(User.class);
                            if (mUser.getAdmin()) {
                                thirdStage.setVisibility(View.VISIBLE);
                                Toasty.success(this,"You Are An Admin User! ",Toast.LENGTH_SHORT,true).show();
                            }
                            if(mUser.getRank() <= 9){
                                secondStage.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
        addEmployee.setOnClickListener(v-> startActivity(new Intent(this,RegisterActivity.class)));
        viewreport.setOnClickListener(v-> startActivity(new Intent(this,ViewReportActivity.class)));
        reports.setOnClickListener(v-> startActivity(new Intent(this,OtherReportActivity.class)));
        paradereports.setOnClickListener(v-> startActivity(new Intent(this,ReportActivity.class)));
        viewParadeReport.setOnClickListener(v-> startActivity(new Intent(this,ViewParadeReportActivity.class)));

    }
}
