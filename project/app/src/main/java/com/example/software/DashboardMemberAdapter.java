package com.example.software;

import static com.example.software.ProductBacklog.mTaskViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.software.provider.Log_Task;
import com.example.software.provider.Members;
import com.example.software.provider.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DashboardMemberAdapter extends RecyclerView.Adapter<DashboardMemberAdapter.ViewHolder>  {

    private List<Members> memberRecycle = new ArrayList<>();

    public void setTask(List<Members> data){
        this.memberRecycle = data;
    }

    public DashboardMemberAdapter() {}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int fPosition = position;
        holder.dashboardName.setText(memberRecycle.get(fPosition).getMemberName());
    }

    @Override
    public int getItemCount() {
        return memberRecycle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView dashboardName;
        public TextView dashboardHour;

        public ViewHolder(View itemView){
            super(itemView);
            dashboardName = itemView.findViewById(R.id.dashboardName);
            dashboardHour = itemView.findViewById(R.id.dashboardHour);
        }
    }
}
