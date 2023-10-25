package com.example.software;

import static com.example.software.ProductBacklog.mTaskViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.software.provider.Task;

import java.util.ArrayList;
import java.util.List;

public class SBAdapter extends RecyclerView.Adapter<SBAdapter.SBViewHolder>  {

    private List<Task> sbListRecycle = new ArrayList<>();

    public void setTask(List<Task> data){
        this.sbListRecycle = data;
    }

    public SBAdapter() {}

    @NonNull
    @Override
    public SBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sprint_allocate_sprint_backlog,parent,false);
        return new SBViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SBViewHolder holder, int position) {
        holder.sbTitle.setText(sbListRecycle.get(position).getName());
        int fPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTaskViewModel.updateSprint(sbListRecycle.get(fPosition).getTaskId(), "PB");
            }
        });
    }

    @Override
    public int getItemCount() {
        return sbListRecycle.size();
    }

    public class SBViewHolder extends RecyclerView.ViewHolder{
        public TextView sbTitle;

        public SBViewHolder(View itemView){
            super(itemView);
            sbTitle = itemView.findViewById(R.id.sbTitle);
        }
    }
}
