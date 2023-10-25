package com.example.software;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.software.provider.TaskViewModel;
import com.example.software.provider.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class ProductBacklog extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;
    ArrayList<Task> taskList;
    RecyclerView.LayoutManager layoutManager;
    ProductBacklogRecyclerViewAdapter adapter;
    Spinner tagSpinner;
    static TaskViewModel mTaskViewModel;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_product_backlog);

        Toolbar toolbar = findViewById(R.id.toolbar_productbacklog);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_product_backlog);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navView_product_backlog);
        navigationView.setNavigationItemSelectedListener(this);

        tagSpinner = findViewById(R.id.filterTagSpinner);
        ArrayAdapter<String> tagAdapter = new ArrayAdapter<String>(ProductBacklog.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.filterTags));
        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(tagAdapter);

        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedTag = (String) adapterView.getItemAtPosition(i);
                System.out.println(selectedTag);
                adapter.getFilter().filter(selectedTag);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        recyclerView = findViewById(R.id.task_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        taskList = new ArrayList<>();
        adapter = new ProductBacklogRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        mTaskViewModel.getAllTasks().observe(this, newData -> {
            adapter.setTask(newData);
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
//            Intent productBacklogIntent = new Intent(getApplicationContext(), ProductBacklog.class);
//            startActivity(productBacklogIntent);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.task_menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }
}