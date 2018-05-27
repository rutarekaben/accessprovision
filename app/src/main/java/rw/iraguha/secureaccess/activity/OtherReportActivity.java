package rw.iraguha.secureaccess.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
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

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import rw.iraguha.secureaccess.R;

public class OtherReportActivity extends AppCompatActivity {
    private static final String TAG = "OtherReportActivity";
    private EditText title,message;
    private Button sendreport;
    private FirebaseFirestore db;
    private AlertDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reports");
        db = FirebaseFirestore.getInstance();
        //construct sport dialog
        progressDialog = new SpotsDialog(this);
        title = findViewById(R.id.title);
        message = findViewById(R.id.message);
        sendreport = findViewById(R.id.sendreport);
        sendreport.setOnClickListener(v->{
            if(isValid()){
                progressDialog.show();
                //adding data to firestore
                // Create a new user with a first and last name
                Map<String, Object> report = new HashMap<>();
                report.put("title", title.getText().toString());
                report.put("message", message.getText().toString());
                report.put("createdAt", FieldValue.serverTimestamp());
                // Add a new document with a generated ID
                db.collection("reports")
                        .add(report)
                        .addOnSuccessListener(documentReference -> {
                            Log.d(TAG, "Report Sent with ID: " + documentReference.getId());
                            progressDialog.dismiss();
                            Toasty.success(getApplicationContext(),"Report Sent Successfully",Toast.LENGTH_SHORT,true).show();
                            title.setText("");
                            message.setText("");
                            onBackPressed();
                        })
                        .addOnFailureListener(e -> {
                            Log.w(TAG, "Error occured while trying to send report in db", e);
                            progressDialog.dismiss();
                            Toasty.error(getApplicationContext(),"Error Occurred try again later",Toast.LENGTH_SHORT,true).show();
                        });
            }else
            {
                Toasty.error(this,"Validation Failed!", Toast.LENGTH_LONG,true).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Boolean isValid(){
        if(TextUtils.isEmpty(title.getText())){
            return false;
        }
        if(TextUtils.isEmpty(message.getText())){
            return false;
        }
        return true;
    }
}
