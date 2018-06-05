package rw.iraguha.secureaccess.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import rw.iraguha.secureaccess.R;
import rw.iraguha.secureaccess.model.Report;

public class ViewReportDetailActivity extends AppCompatActivity {

    private TextView title,message;
    Report mReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View reports Detail");

        title =  findViewById(R.id.title);
        message = findViewById(R.id.message);

        mReport = getIntent().getParcelableExtra("report");

        if(mReport != null){
                title.setText(mReport.getTitle());
                message.setText(mReport.getMessage());
        }
    }
}
