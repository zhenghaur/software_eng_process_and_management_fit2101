package com.example.software;

import static com.example.software.ProductBacklog.mTaskViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.software.provider.Sprint;

import java.util.ArrayList;
import java.util.List;

public class SprintBoardAdapter extends RecyclerView.Adapter<SprintBoardAdapter.ViewHolder>  {

    private List<Sprint> sprintListRecycle = new ArrayList<>();
    private List<Sprint> sprintListRecycleFull;
    Context context;


    public void setSprint(List<Sprint> data){
        this.sprintListRecycle = data;
        sprintListRecycleFull = new ArrayList<>(data);
    }

    public SprintBoardAdapter(Context context) { this.context = context;}

    @NonNull
    @Override
    public SprintBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sprint_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SprintBoardAdapter.ViewHolder holder, int position) {
        holder.sprintName.setText(sprintListRecycle.get(position).getSprintName());
        final int fPosition = position;



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sprintListRecycle.get(fPosition).getSprintName();
                Intent intent = new Intent(context.getApplicationContext(), SprintOverview.class);
                intent.putExtra("keyName",name);
                context.startActivity(intent);

                // intent code to zh page
            }
        });
    }

    @Override
    public int getItemCount() {
        return sprintListRecycle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView sprintName;
        public ImageView goToAddSprint;

        public ViewHolder(View itemView){
            super(itemView);
            sprintName = itemView.findViewById(R.id.sprintNameCard);
//            goToAddSprint = itemView.findViewById(R.id.imageEditCard);
        }
    }
}