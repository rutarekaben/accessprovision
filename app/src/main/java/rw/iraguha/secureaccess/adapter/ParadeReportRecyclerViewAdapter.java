package rw.iraguha.secureaccess.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rw.iraguha.secureaccess.R;
import rw.iraguha.secureaccess.activity.ViewParadeReportDetailActivity;
import rw.iraguha.secureaccess.activity.ViewReportDetailActivity;
import rw.iraguha.secureaccess.model.ParadeReport;
import rw.iraguha.secureaccess.model.Report;


public class ParadeReportRecyclerViewAdapter extends RecyclerView.Adapter<ParadeReportRecyclerViewAdapter.ViewHolder> {
    public Context context;
    public List<ParadeReport> reportList;

    public ParadeReportRecyclerViewAdapter(Context context, List<ParadeReport> reportList){
        this.context  = context;
        this.reportList =reportList;
    }

    @NonNull
    @Override
    public ParadeReportRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParadeReportRecyclerViewAdapter.ViewHolder holder, int position) {
        ParadeReport model = reportList.get(position);
        holder.title.setText(model.getDate()+"/"+model.getMonth()+"/"+model.getYear());
        holder.card_view.setOnClickListener(v->{
            Intent mIntent = new Intent(v.getContext(),ViewParadeReportDetailActivity.class);
            mIntent.putExtra("parade", model.getData().get(0));
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