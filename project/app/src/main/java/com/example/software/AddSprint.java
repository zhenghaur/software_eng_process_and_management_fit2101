package com.example.software;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.software.provider.Sprint;
import com.example.software.provider.TaskViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddSprint extends AppCompatActivity {
    final Calendar myStartCalendar = Calendar.getInstance();
    final Calendar myEndCalendar = Calendar.getInstance();
    EditText startDateInput;
    EditText endDateInput;
    EditText sprintNameInput;
    Button addSprint;

    static TaskViewModel mTaskViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sprint);
        startDateInput = findViewById(R.id.chooseSprintStartDate);
        endDateInput = findViewById(R.id.chooseSprintEndDate);
        sprintNameInput = findViewById(R.id.memberNameInput);
        addSprint = findViewById(R.id.addSprintButton);

        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);


        DatePickerDialog.OnDateSetListener start_date = (view, year, month, day) -> {
            myStartCalendar.set(Calendar.YEAR, year);
            myStartCalendar.set(Calendar.MONTH,month);
            myStartCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateStartLabel();
        };

        DatePickerDialog.OnDateSetListener end_date = (view, year, month, day) -> {
            myEndCalendar.set(Calendar.YEAR, year);
            myEndCalendar.set(Calendar.MONTH, month);
            myEndCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateEndLabel();
        };

        startDateInput.setOnClickListener(view -> new DatePickerDialog(AddSprint.this,start_date, myStartCalendar.get(Calendar.YEAR), myStartCalendar.get(Calendar.MONTH), myStartCalendar.get(Calendar.DAY_OF_MONTH)).show());
        endDateInput.setOnClickListener(view -> new DatePickerDialog(AddSprint.this,end_date, myEndCalendar.get(Calendar.YEAR), myEndCalendar.get(Calendar.MONTH), myEndCalendar.get(Calendar.DAY_OF_MONTH)).show());


        addSprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sprintNameInString = sprintNameInput.getText().toString();
                String sprintStartDateInString = startDateInput.getText().toString();
                String sprintEndDateInString = endDateInput.getText().toString();

                if (sprintNameInString.isEmpty() | sprintStartDateInString.isEmpty() |
                sprintEndDateInString.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Sprint sprint = new Sprint(sprintNameInString, sprintStartDateInString, sprintEndDateInString);
                    mTaskViewModel.addSprint(sprint);

                    Toast.makeText(AddSprint.this, "" + sprintNameInString + " has been" +
                            " added.", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });


    }

    private void updateStartLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        startDateInput.setText(dateFormat.format(myStartCalendar.getTime()));
    }

    private void updateEndLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        endDateInput.setText(dateFormat.format(myEndCalendar.getTime()));
    }
}

