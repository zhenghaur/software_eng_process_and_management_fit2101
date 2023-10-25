package com.example.software;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.software.provider.Members;
import com.example.software.provider.Sprint;
import com.example.software.provider.TaskViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class TeamMembersList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Members> members = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TeamMembersViewAdapter adapter;

    static TaskViewModel mTeamMembersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_team_members_list);

        Button goToAddMember = findViewById(R.id.goToAddTeamMembers);

        goToAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTeamMember.class);
                startActivity(intent);
            }
        });

        toolbar = findViewById(R.id.toolbar_team_members_list);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_team_members_list);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navView_team_members_list);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.team_members_recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TeamMembersViewAdapter(this);
        adapter.setMember(members);
        recyclerView.setAdapter(adapter);

        mTeamMembersViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        mTeamMembersViewModel.getAllTeamMembers().observe(this, newData -> {
            adapter.setMember(newData);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.goToAddTask) {
            Intent addTaskIntent = new Intent(getApplicationContext(), AddTask.class);
            startActivity(addTaskIntent);
        }
        else if (id == R.id.goToProductBackLog) {
            Intent productBacklogIntent = new Intent(getApplicationContext(), ProductBacklog.class);
            startActivity(productBacklogIntent);
        }
        else if (id == R.id.goToSprintBoard) {
            Intent sprintBoardIntent = new Intent(getApplicationContext(), SprintBoard.class);
            startActivity(sprintBoardIntent);
        }
        else if (id == R.id.goToTeamMembers) {
//            Intent teamMembersIntent = new Intent(getApplicationContext(), TeamMembersList.class);
//            startActivity(teamMembersIntent);
        }
        else if (id == R.id.goToHomePage) {
            Intent homePageIntent = new Intent(getApplicationContext(), HomePage.class);
            startActivity(homePageIntent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}

