package com.example.software;

import static com.example.software.ProductBacklog.mTaskViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.software.provider.Task;
import com.example.software.provider.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class SprintAllocate extends AppCompatActivity {

    private RecyclerView productBacklogList,sprintBacklogList;
    String sprintName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        sprintName = intent.getStringExtra("keyName");
        setContentView(R.layout.sprint_allocate);

        // product backlog
        productBacklogList = findViewById(R.id.productBacklogList);
        productBacklogList.setHasFixedSize(true);
        productBacklogList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        List<Task> pbList = new ArrayList<>();

        PBAdapter pbAdapter = new PBAdapter(sprintName);
        productBacklogList.setAdapter(pbAdapter);

        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        mTaskViewModel.getSprintTasks("PB").observe(this, newData -> {
            pbAdapter.setTask(newData);
            pbAdapter.notifyDataSetChanged();
        });

        // sprint backlog
        sprintBacklogList = findViewById(R.id.sprintBacklogList);
        sprintBacklogList.setHasFixedSize(true);
        sprintBacklogList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        SBAdapter sbAdapter = new SBAdapter();
        sprintBacklogList.setAdapter(sbAdapter);

        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        mTaskViewModel.getSprintTasks(sprintName).observe(this, newData -> {
            sbAdapter.setTask(newData);
            sbAdapter.notifyDataSetChanged();
        });

        Button startSprintButton = findViewById(R.id.startSprintButton);
        startSprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}

