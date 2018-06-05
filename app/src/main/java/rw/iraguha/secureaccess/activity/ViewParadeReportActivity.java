package rw.iraguha.secureaccess.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import rw.iraguha.secureaccess.R;
import rw.iraguha.secureaccess.adapter.ParadeReportRecyclerViewAdapter;
import rw.iraguha.secureaccess.adapter.ReportRecyclerViewAdapter;
import rw.iraguha.secureaccess.model.ParadeReport;
import rw.iraguha.secureaccess.model.Report;

public class ViewParadeReportActivity extends AppCompatActivity {
    ProgressBar progressBar;
    RecyclerView reportList;
    private FirebaseFirestore db;
    private ParadeReportRecyclerViewAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    public List<ParadeReport> data;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        progressBar = findViewById(R.id.progress_bar);
        reportList = findViewById(R.id.recycler_view);
        init();
        mContext = this;
        getReportList();
    }

    private void init(){
        data = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        reportList.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();
        adapter = new ParadeReportRecyclerViewAdapter(mContext,data);
        reportList.setAdapter(adapter);
    }

    private void getReportList(){
        Query query = db.collection("parade");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        data.add(documentSnapshot.toObject(ParadeReport.class));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.error(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG,true).show();
            }
        });
    }
}
