package rw.iraguha.secureaccess.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import rw.iraguha.secureaccess.R;
import rw.iraguha.secureaccess.model.Parade;
import rw.iraguha.secureaccess.model.ParadeReport;
import rw.iraguha.secureaccess.model.Report;

public class ViewParadeReportDetailActivity extends AppCompatActivity {

    private TextView abs,adm,duty,onp,pass,rank,sick,description,total;
    Parade mReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_parade_report_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Parade Report Details");
        abs =  findViewById(R.id.abs);
        adm = findViewById(R.id.adm);
        duty = findViewById(R.id.duty);
        onp = findViewById(R.id.onp);
        pass = findViewById(R.id.pass);
        rank = findViewById(R.id.rank);
        sick = findViewById(R.id.sick);
        description = findViewById(R.id.description);
        total = findViewById(R.id.total);

        mReport = getIntent().getParcelableExtra("parade");

        if(mReport != null){
            abs.setText(mReport.getAbs());
            adm.setText(mReport.getAdm());
            duty.setText(mReport.getDuty());
            onp.setText(mReport.getOnp());
            pass.setText(mReport.getPass());
            rank.setText(mReport.getRank());
            sick.setText(mReport.getSick());
            description.setText(mReport.getDescription());
            total.setText(mReport.getTotal());
        }
    }
}
