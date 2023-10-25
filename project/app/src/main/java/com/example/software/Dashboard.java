package com.example.software;

import static com.example.software.ProductBacklog.mTaskViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.software.provider.TaskViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dashboard extends AppCompatActivity {
    final Calendar myStartCalendar= Calendar.getInstance();
    final Calendar myEndCalendar= Calendar.getInstance();
    EditText startDateInput;
    EditText endDateInput;
    double dateRange;
    Date myStartDate, myEndDate;
    RecyclerView dashboardRecyclerView;

//    static TaskViewModel mTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        startDateInput = findViewById(R.id.chooseStartDate);
        endDateInput = findViewById(R.id.chooseEndDate);

        dashboardRecyclerView = findViewById(R.id.dashboardRecyclerView);
        dashboardRecyclerView.setHasFixedSize(true);
        dashboardRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        DashboardMemberAdapter dashboardMemberAdapter = new DashboardMemberAdapter();
        dashboardRecyclerView.setAdapter(dashboardMemberAdapter);

        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        mTaskViewModel.getAllTeamMembers().observe(this, newData -> {
            dashboardMemberAdapter.setTask(newData);
            dashboardMemberAdapter.notifyDataSetChanged();
        });

        DatePickerDialog.OnDateSetListener start_date = (view, year, month, day) -> {
            myStartCalendar.set(Calendar.YEAR, year);
            myStartCalendar.set(Calendar.MONTH, month);
            myStartCalendar.set(Calendar.DAY_OF_MONTH, day);
            myStartCalendar.set(Calendar.HOUR, 0);
            myStartCalendar.set(Calendar.MINUTE, 0);
            myStartCalendar.set(Calendar.SECOND, 0);
            myStartCalendar.set(Calendar.MILLISECOND, 0);
            updateStartLabel();
            myStartDate = new Date(myStartCalendar.getTimeInMillis());
        };
        DatePickerDialog.OnDateSetListener end_date = (view, year, month, day) -> {
            myEndCalendar.set(Calendar.YEAR, year);
            myEndCalendar.set(Calendar.MONTH, month);
            myEndCalendar.set(Calendar.DAY_OF_MONTH, day);
            myEndCalendar.set(Calendar.HOUR, 0);
            myEndCalendar.set(Calendar.MINUTE, 0);
            myEndCalendar.set(Calendar.SECOND, 0);
            myEndCalendar.set(Calendar.MILLISECOND, 0);
            updateEndLabel();
            myEndDate = new Date(myEndCalendar.getTimeInMillis());
        };
        startDateInput.setOnClickListener(view -> new DatePickerDialog(Dashboard.this,start_date,myStartCalendar.get(Calendar.YEAR),myStartCalendar.get(Calendar.MONTH),myStartCalendar.get(Calendar.DAY_OF_MONTH)).show());
        endDateInput.setOnClickListener(view -> new DatePickerDialog(Dashboard.this,end_date,myEndCalendar.get(Calendar.YEAR),myEndCalendar.get(Calendar.MONTH),myEndCalendar.get(Calendar.DAY_OF_MONTH)).show());

        Button selectRange = findViewById(R.id.selectRange);
        selectRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get number of days between the two chosen date range
                if (myStartCalendar.get(Calendar.MONTH) == myEndCalendar.get(Calendar.MONTH)) {
                    dateRange = myEndCalendar.get(Calendar.DAY_OF_MONTH) - myStartCalendar.get(Calendar.DAY_OF_MONTH) + 1;
                } else {
                    int difference = myEndCalendar.get(Calendar.MONTH) - myStartCalendar.get(Calendar.MONTH);
                    int lastDateOfMonth = myStartCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    int bounded = lastDateOfMonth * difference;
                    dateRange = bounded + myEndCalendar.get(Calendar.DAY_OF_MONTH) - myStartCalendar.get(Calendar.DAY_OF_MONTH) + 1;
                }

                if ((myStartCalendar.getTimeInMillis() == myEndCalendar.getTimeInMillis()) && !startDateInput.getText().toString().isEmpty() && !endDateInput.getText().toString().isEmpty()) {
                    for (int i = 0; i < dashboardRecyclerView.getChildCount(); i++) {
                        DashboardMemberAdapter.ViewHolder holder = (DashboardMemberAdapter.ViewHolder) dashboardRecyclerView.findViewHolderForAdapterPosition(i);
                        ExecutorService executorService = Executors.newFixedThreadPool(4);
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                holder.dashboardHour.setText(String.valueOf(mTaskViewModel.getHoursBetweenDates(myStartDate, myEndDate, String.valueOf(holder.dashboardName.getText()))) + ".00 hours");
                            }
                        });
                    }
                }else if ((myStartCalendar.getTimeInMillis() < myEndCalendar.getTimeInMillis()) && !startDateInput.getText().toString().isEmpty() && !endDateInput.getText().toString().isEmpty()) {
                    for (int i = 0; i < dashboardRecyclerView.getChildCount(); i++) {
                        DashboardMemberAdapter.ViewHolder holder = (DashboardMemberAdapter.ViewHolder) dashboardRecyclerView.findViewHolderForAdapterPosition(i);
                        ExecutorService executorService = Executors.newFixedThreadPool(4);
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                holder.dashboardHour.setText(String.format("%.2f", mTaskViewModel.getHoursBetweenDates(myStartDate, myEndDate, String.valueOf(holder.dashboardName.getText())) / dateRange) + " hours");
                            }
                        });
                    }
                } else if (startDateInput.getText().toString().isEmpty() | endDateInput.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please choose a start date and an end date.",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "End date cannot be set to a date earlier than start date.",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void updateStartLabel () {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        startDateInput.setText(dateFormat.format(myStartCalendar.getTime()));
    }
    private void updateEndLabel () {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        endDateInput.setText(dateFormat.format(myEndCalendar.getTime()));
    }
}