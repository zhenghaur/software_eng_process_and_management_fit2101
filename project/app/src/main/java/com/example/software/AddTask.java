package com.example.software;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.software.provider.Members;
import com.example.software.provider.MembersDao;
import com.example.software.provider.Task;
import com.example.software.provider.TaskDao;
import com.example.software.provider.TaskViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RadioGroup radioCategoryGroup;
    DrawerLayout drawerLayout;

    static MembersDao mMembersDao;

    static TaskViewModel mTaskViewModel;
    ArrayList<String> membersList = new ArrayList<>();

//    ArrayAdapter myAdapter;
    ProductBacklogRecyclerViewAdapter adapter;
    TeamMembersViewAdapter teamMembersViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_add_task);

        Toolbar toolbar = findViewById(R.id.toolbar_addtask);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_add_task);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navView_add_task);
        navigationView.setNavigationItemSelectedListener(this);


        adapter = new ProductBacklogRecyclerViewAdapter(this);
        teamMembersViewAdapter = new TeamMembersViewAdapter(this);
        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        mTaskViewModel.getAllTasks().observe(this, newData -> {
            adapter.setTask(newData);
            adapter.notifyDataSetChanged();
        });


        // Dropdown list Values
        Spinner prioritySpinner = (Spinner) findViewById(R.id.priorityBox);
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(AddTask.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.priority));
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(priorityAdapter);

        Spinner statusSpinner = (Spinner) findViewById(R.id.statusBox);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(AddTask.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.status));
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        Spinner assignSpinner = (Spinner) findViewById(R.id.assignedBox);
        ArrayAdapter<String> assignAdapter = new ArrayAdapter<String>(AddTask.this,
                android.R.layout.simple_list_item_1, membersList);
        assignAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignSpinner.setAdapter(assignAdapter);

        membersList.add("None");
        mTaskViewModel.getAllTeamMembers().observe(this, new Observer<List<Members>>() {
            @Override
            public void onChanged(@Nullable final List<Members> member) {
                for (int i = 0; i < member.size(); i++) {
                    membersList.add(member.get(i).getMemberName());
                }
                assignAdapter.notifyDataSetChanged();
            }

        });


        Spinner tagSpinner = (Spinner) findViewById(R.id.tagBox);
        ArrayAdapter<String> tagAdapter = new ArrayAdapter<String>(AddTask.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.tags));
        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(tagAdapter);

        radioCategoryGroup = (RadioGroup) findViewById(R.id.radioCategory);
        radioCategoryGroup.check(R.id.userStoryButton);
        int selectedId = radioCategoryGroup.getCheckedRadioButtonId();
        RadioButton taskCategory = (RadioButton)findViewById(selectedId);

        EditText taskName = findViewById(R.id.nameBox);
        taskName.setText("");

        EditText taskSP = findViewById(R.id.storyPointsBox);
        taskSP.setText("");

        Spinner taskPriority = (Spinner)findViewById(R.id.priorityBox);

        Spinner taskStatus = (Spinner)findViewById(R.id.statusBox);

        Spinner taskAssigned = (Spinner)findViewById(R.id.assignedBox);

        Spinner taskTag = (Spinner)findViewById(R.id.tagBox);

        EditText taskDescription = findViewById(R.id.descriptionBox);
        taskDescription.setText("");

        Button create = findViewById(R.id.createButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = taskCategory.getText().toString();
                String name = taskName.getText().toString();
                String priority = taskPriority.getSelectedItem().toString();
                String status = taskStatus.getSelectedItem().toString();
                String assigned = taskAssigned.getSelectedItem().toString();
                String tag = taskTag.getSelectedItem().toString();
                String sp = taskSP.getText().toString();
                String description = taskDescription.getText().toString();
                String sprint = "PB";

                if (name.isEmpty() | sp.isEmpty() | description.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Task task = new Task(category, name, description, priority, status, assigned, tag, Integer.parseInt(sp),sprint);
                    mTaskViewModel.insert(task);
                    Toast.makeText(getApplicationContext(), "Task successfully created.", Toast.LENGTH_SHORT).show();

                    // reset fields after creating a tas2k
                    radioCategoryGroup.clearCheck();
                    taskName.setText("");
                    taskPriority.setSelection(0);
                    taskStatus.setSelection(0);
                    taskAssigned.setSelection(0);
                    taskTag.setSelection(0);
                    taskSP.setText("");
                    taskDescription.setText("");
                }
            }
        });

//        Button productBacklog = findViewById(R.id.goNext);
//        productBacklog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), SprintAllocate.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.goToAddTask) {
//            Intent addTaskIntent = new Intent(getApplicationContext(), AddTask.class);
//            startActivity(addTaskIntent);
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
            Intent homePageIntent = new Intent(getApplicationContext(), HomePage.class);
            startActivity(homePageIntent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}