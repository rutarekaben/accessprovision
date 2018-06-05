package rw.iraguha.secureaccess.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rw.iraguha.secureaccess.R;
import rw.iraguha.secureaccess.activity.ViewReportActivity;
import rw.iraguha.secureaccess.activity.ViewReportDetailActivity;
import rw.iraguha.secureaccess.model.Report;


public class ReportRecyclerViewAdapter extends RecyclerView.Adapter<ReportRecyclerViewAdapter.ViewHolder> {
    public Context context;
    public List<Report> reportList;

    public ReportRecyclerViewAdapter(Context context,List<Report> reportList){
        this.context  = context;
        this.reportList =reportList;
    }

    @NonNull
    @Override
    public ReportRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportRecyclerViewAdapter.ViewHolder holder, int position) {
        Report model = reportList.get(position);
        holder.title.setText(model.getTitle());
        holder.card_view.setOnClickListener(v->{
            Intent mIntent = new Intent(v.getContext(),ViewReportDetailActivity.class);
            mIntent.putExtra("report", model);
            v.getContext().startActivity(mIntent);
        });
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView title;
        CardView card_view;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}