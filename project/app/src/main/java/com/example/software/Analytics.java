package com.example.software;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;


import com.example.software.provider.TaskViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Analytics extends AppCompatActivity {

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList = new ArrayList();

    //member id
    int id;
    String name;

    static TaskViewModel mTaskViewModel;
    ExecutorService executorService = Executors.newFixedThreadPool(8);

    Date today = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analytics);

        setBarChart();

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        id = intent.getIntExtra("keyID",0);

        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        // setting page title
        TextView title = findViewById(R.id.analytics_title);
        title.setText(name + " Analytics");

    }
    private void setBarChart() {
        // creating a new array list
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                barChart = findViewById(R.id.idBarChart);

                // creating entries
                barEntriesArrayList = new ArrayList<>();
                barEntriesArrayList.add(new BarEntry(1f, mTaskViewModel.getDurationByMemberID(id, new Date(today.getTime() - (6 * DateUtils.DAY_IN_MILLIS)))));
                barEntriesArrayList.add(new BarEntry(2f, mTaskViewModel.getDurationByMemberID(id, new Date(today.getTime() - (5 * DateUtils.DAY_IN_MILLIS)))));
                barEntriesArrayList.add(new BarEntry(3f, mTaskViewModel.getDurationByMemberID(id, new Date(today.getTime() - (4 * DateUtils.DAY_IN_MILLIS)))));
                barEntriesArrayList.add(new BarEntry(4f, mTaskViewModel.getDurationByMemberID(id, new Date(today.getTime() - (3 * DateUtils.DAY_IN_MILLIS)))));
                barEntriesArrayList.add(new BarEntry(5f, mTaskViewModel.getDurationByMemberID(id, new Date(today.getTime() - (2 * DateUtils.DAY_IN_MILLIS)))));
                barEntriesArrayList.add(new BarEntry(6f, mTaskViewModel.getDurationByMemberID(id, new Date(today.getTime() - (1 * DateUtils.DAY_IN_MILLIS)))));
                barEntriesArrayList.add(new BarEntry(7f, mTaskViewModel.getDurationByMemberID(id, new Date(today.getTime() - (0 * DateUtils.DAY_IN_MILLIS)))));

                barDataSet = new BarDataSet(barEntriesArrayList, "Past 7 days time spent");

                // creating a new bar data and
                // passing our bar data set.
                barData = new BarData(barDataSet);

                // below line is to set data
                // to our bar chart.
                barChart.setData(barData);

                // adding color to our bar data set.
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                // setting color.
                barDataSet.setValueTextColor(Color.BLACK);
                barChart.setDrawGridBackground(false);

                // customizing barchart
                barDataSet.setValueTextSize(16f);
                barChart.getDescription().setEnabled(false);
                barChart.setBackgroundColor(Color.parseColor("#FFFFFF"));
                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        String[] DAYS = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };
                        Date today = new Date();
                        return DAYS[ ((int) (value + today.getDay())) % 7];
                    }
                });


            }
        });





    }
}
