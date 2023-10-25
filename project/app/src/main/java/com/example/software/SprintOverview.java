package com.example.software;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.software.provider.Task;
import com.example.software.provider.TaskViewModel;

import java.util.ArrayList;

public class SprintOverview extends AppCompatActivity {
    RecyclerView completedRecycler;
    RecyclerView inProgressRecycler;
    RecyclerView notStartedRecycler;
    ArrayList<Task> taskList;
    RecyclerView.LayoutManager completedLayoutManager;
    RecyclerView.LayoutManager inProgressLayoutManager;
    RecyclerView.LayoutManager notStartedLayoutManager;
    SprintRecyclerViewAdapter completedAdapter;
    SprintRecyclerViewAdapter inProgressAdapter;
    SprintRecyclerViewAdapter notStartedAdapter;
    static TaskViewModel mTaskViewModel;
    String sprintName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprint_overview);
        Intent intent = getIntent();
        sprintName = intent.getStringExtra("keyName");

        TextView sprintOverview = findViewById(R.id.sprintOverview);
        sprintOverview.setText(sprintName + " Overview");

        completedRecycler = findViewById(R.id.completed_recycler_view);
        completedLayoutManager = new LinearLayoutManager(this);
        completedRecycler.setLayoutManager(completedLayoutManager);

        inProgressRecycler = findViewById(R.id.in_progress_recycler_view);
        inProgressLayoutManager = new LinearLayoutManager(this);
        inProgressRecycler.setLayoutManager(inProgressLayoutManager);

        notStartedRecycler = findViewById(R.id.not_started_recycler_view);
        notStartedLayoutManager = new LinearLayoutManager(this);
        notStartedRecycler.setLayoutManager(notStartedLayoutManager);

        taskList = new ArrayList<>();

        completedAdapter = new SprintRecyclerViewAdapter(this);
        completedRecycler.setAdapter(completedAdapter);

        inProgressAdapter = new SprintRecyclerViewAdapter(this);
        inProgressRecycler.setAdapter(inProgressAdapter);

        notStartedAdapter = new SprintRecyclerViewAdapter(this);
        notStartedRecycler.setAdapter(notStartedAdapter);

        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        mTaskViewModel.getSprintStatus(sprintName,"Completed").observe(this, newData -> {
            completedAdapter.setTask(newData);
            completedAdapter.notifyDataSetChanged();
        });

        mTaskViewModel.getSprintStatus2(sprintName,"In Progress", "Developing", "Testing").observe(this, newData -> {
            inProgressAdapter.setTask(newData);
            inProgressAdapter.notifyDataSetChanged();
        });

        mTaskViewModel.getSprintStatus(sprintName,"Not Started").observe(this, newData -> {
            notStartedAdapter.setTask(newData);
            notStartedAdapter.notifyDataSetChanged();
        });

        Button allocateTask = findViewById(R.id.sprint_allocate_task);
        allocateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SprintAllocate.class);
                intent.putExtra("keyName", sprintName);
                startActivity(intent);
            }
        });

        Button end = findViewById(R.id.end_sprint_overview);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mTaskViewModel.deleteSprintByName(sprintName);
                mTaskViewModel.endSprint(sprintName);
                finish();
            }
        });
    }

}