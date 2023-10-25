package com.example.software;

import static com.example.software.TeamMembersList.mTeamMembersViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.software.provider.Members;
import com.example.software.provider.Sprint;

import java.util.ArrayList;
import java.util.List;

public class TeamMembersViewAdapter extends RecyclerView.Adapter<TeamMembersViewAdapter.ViewHolder> {

    private List<Members> membersListRecycle = new ArrayList<>();
    private List<Members> membersListRecycleFull;
    Context context;

    public void setMember(List<Members> data) {
        this.membersListRecycle = data;
        membersListRecycleFull = new ArrayList<>(data);
    }

    public TeamMembersViewAdapter(Context context) { this.context = context;}

    @NonNull
    @Override
    public TeamMembersViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_member_card,parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamMembersViewAdapter.ViewHolder holder, int position) {
        holder.memberInfoCard.setText(membersListRecycle.get(position).getMemberName() + " - " +
                 membersListRecycle.get(position).getMemberEmail());

        int finalPosition = position;
        holder.analyticsView.setOnClickListener(new View.OnClickListener() {
            int id = membersListRecycle.get(finalPosition).getMemberID();
            String memberName = membersListRecycle.get(finalPosition).getMemberName();

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), Analytics.class);
                intent.putExtra("name", memberName);
                intent.putExtra("keyID", id);
                context.startActivity(intent);
            }
        });
        holder.deleteMember.setOnClickListener(new View.OnClickListener() {
            int id = membersListRecycle.get(finalPosition).getMemberID();
            String memberName = membersListRecycle.get(finalPosition).getMemberName();

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Removing " + memberName)
                        .setMessage("Would you like to remove this team member?")
                        .setCancelable(true)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mTeamMembersViewModel.deleteTeamMemberByID(id);

                            }
                        })
                        .show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return membersListRecycle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView memberInfoCard;
        public ImageView deleteMember;
        public Button analyticsView;

        public ViewHolder(View itemView) {
            super(itemView);
            memberInfoCard = itemView.findViewById(R.id.memberInfoCard);
            deleteMember = itemView.findViewById(R.id.deleteMember);
            analyticsView = itemView.findViewById(R.id.analyticsButton);
        }
    }
}
