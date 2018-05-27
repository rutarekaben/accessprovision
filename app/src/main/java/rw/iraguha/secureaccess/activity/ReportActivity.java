package rw.iraguha.secureaccess.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import rw.iraguha.secureaccess.R;
import rw.iraguha.secureaccess.model.Parade;
import rw.iraguha.secureaccess.model.ParadeReport;

public class ReportActivity extends AppCompatActivity {
    private static final String TAG = "ReportActivity";
    private Button sendreport;
    private EditText date,month,year,district,abs,adm,closearr,duty,onp,pass,rank,sick,sickout,total;
    private FirebaseFirestore db;
    private AlertDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        date = findViewById(R.id.date);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        district = findViewById(R.id.district);
        abs = findViewById(R.id.abs);
        adm = findViewById(R.id.adm);
        closearr = findViewById(R.id.closearr);
        duty = findViewById(R.id.duty);
        onp = findViewById(R.id.onp);
        pass = findViewById(R.id.pass);
        rank = findViewById(R.id.rank);
        sick = findViewById(R.id.sick);
        sickout = findViewById(R.id.sickout);
        total = findViewById(R.id.total);
        sendreport = findViewById(R.id.sendreport);

        //construct sport dialog
        progressDialog = new SpotsDialog(this);
        db = FirebaseFirestore.getInstance();

        sendreport.setOnClickListener(v->{
            if(isValid()){

//                Map<String,String> paradereport = new HashMap<>();
//                paradereport.put("abs",abs.getText().toString());
//                paradereport.put("adm",adm.getText().toString());
//                paradereport.put("closearr",closearr.getText().toString());
//                paradereport.put("duty",duty.getText().toString());
//                paradereport.put("onp",onp.getText().toString());
//                paradereport.put("pass",pass.getText().toString());
//                paradereport.put("rank",rank.getText().toString());
//                paradereport.put("sick",sick.getText().toString());
//                paradereport.put("sickout",sickout.getText().toString());
//                paradereport.put("total",total.getText().toString());
//                Map<String, Object> user = new HashMap<>();
//                user.put("date", date.getText().toString());
//                user.put("district", district.getText().toString());
//                user.put("month", month.getText().toString());
//                user.put("year", year.getText().toString());
//                user.put("data",paradereport);


                progressDialog.show();
                //adding data to firestore
                ParadeReport mParadeReport = new ParadeReport();
                Parade mParade = new Parade();
                List<Parade> data = new ArrayList<>();
                mParade.setAbs(abs.getText().toString());
                mParade.setAdm(adm.getText().toString());
                mParade.setClosearr(closearr.getText().toString());
                mParade.setDuty(duty.getText().toString());
                mParade.setOnp(onp.getText().toString());
                mParade.setPass(pass.getText().toString());
                mParade.setRank(rank.getText().toString());
                mParade.setSick(sick.getText().toString());
                mParade.setSickout(sickout.getText().toString());
                mParade.setTotal(total.getText().toString());
                data.add(mParade);
                mParadeReport.setDate(date.getText().toString());
                mParadeReport.setMonth(month.getText().toString());
                mParadeReport.setYear(year.getText().toString());
                mParadeReport.setDistrict(district.getText().toString());
                mParadeReport.setData(data);
                // Add a new document with a generated ID
                db.collection("parade")
                        .add(mParadeReport)
                        .addOnSuccessListener(documentReference -> {
                            Log.d(TAG, "parde report added with ID: " + documentReference.getId());
                            progressDialog.dismiss();
                            Toasty.success(getApplicationContext(),"parade report sent Successfully",Toast.LENGTH_SHORT,true).show();
                        })
                        .addOnFailureListener(e -> {
                            Log.w(TAG, "Error occurred while sending parade report in db", e);
                            progressDialog.dismiss();
                            Toasty.error(getApplicationContext(),"Error Occurred try again later",Toast.LENGTH_SHORT,true).show();
                        });
            }else
            {
                Toasty.error(this,"validation failed!", Toast.LENGTH_LONG,true).show();
            }
        });



    }

    private Boolean isValid(){
        if(TextUtils.isEmpty(date.getText())){
            return false;
        }
        if(TextUtils.isEmpty(month.getText())){
            return false;
        }
        if(TextUtils.isEmpty(year.getText())){
            return false;
        }
        if(TextUtils.isEmpty(district.getText())){
            return false;
        }
        if(TextUtils.isEmpty(abs.getText())){
            return false;
        }
        if(TextUtils.isEmpty(adm.getText())){
            return false;
        }
        if(TextUtils.isEmpty(closearr.getText())){
            return false;
        }
        if(TextUtils.isEmpty(duty.getText())){
            return false;
        }
        if(TextUtils.isEmpty(onp.getText())){
            return false;
        }
        if(TextUtils.isEmpty(pass.getText())){
            return false;
        }
        if(TextUtils.isEmpty(rank.getText())){
            return false;
        }
        if(TextUtils.isEmpty(sick.getText())){
            return false;
        }
        if(TextUtils.isEmpty(sickout.getText())){
            return false;
        }
        if(TextUtils.isEmpty(total.getText())){
            return false;
        }
        return true;
    }

}
