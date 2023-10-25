package com.example.software;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button addMemberButton;
    Button dashboardButton;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_home_screen);

        Toolbar toolbar = findViewById(R.id.toolbar_home_screen);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_home_screen);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navView_home_screen);
        navigationView.setNavigationItemSelectedListener(this);

        addMemberButton = findViewById(R.id.addMemberButton);
        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTeamMember.class);
                startActivity(intent);
            }
        });

        dashboardButton = findViewById(R.id.dashboardButton);
        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
            }
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
            Intent teamMembersIntent = new Intent(getApplicationContext(), TeamMembersList.class);
            startActivity(teamMembersIntent);
        }

        else if (id == R.id.goToHomePage) {
//            Intent homePageIntent = new Intent(getApplicationContext(), HomePage.class);
//            startActivity(homePageIntent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}