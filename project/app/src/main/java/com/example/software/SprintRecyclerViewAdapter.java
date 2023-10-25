package com.example.software;

import static com.example.software.SprintOverview.mTaskViewModel;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.example.software.provider.Members;
import com.example.software.provider.Task;
import com.example.software.provider.Log_Task;
import com.example.software.provider.TaskDateTime;
import com.example.software.provider.TaskViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SprintRecyclerViewAdapter extends RecyclerView.Adapter<SprintRecyclerViewAdapter.ViewHolder> {
    List<Task> taskListRecycle = new ArrayList<>();
    List<Log_Task> taskDateHoursListR = new ArrayList<>();
    Database database;
    Context context;
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    ArrayList<String> membersList = new ArrayList<>();
    public void setTask(List<Task> data){
        this.taskListRecycle = data;
    }


    public SprintRecyclerViewAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public SprintRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sprint_task_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SprintRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.sprintTaskTitle.setText(taskListRecycle.get(position).getName());
        if (taskListRecycle.get(position).getStatus().equals("Not Started")){
            holder.sprintTaskStatus.setBackgroundColor(Color.parseColor("#830000"));
        } else if (taskListRecycle.get(position).getStatus().equals("Completed")){
            holder.sprintTaskStatus.setBackgroundColor(Color.parseColor("#004614"));
        } else {
            holder.sprintTaskStatus.setBackgroundColor(Color.parseColor("#0D0C6F"));
        }

        int fPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View view1 = LayoutInflater.from(context).inflate(R.layout.log_time_spent, null);
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final Calendar myCalendar= Calendar.getInstance();

//                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
//                DialogFragment DateFragment = new DatePickerFragment();
//                DateFragment.show(manager, "datePicker");

                final EditText logName = view1.findViewById(R.id.logName);
                final EditText logSP = view1.findViewById(R.id.logSP);
                final EditText logDesc = view1.findViewById(R.id.logDesc);
                final EditText logHours = view1.findViewById(R.id.logWorkTime);
                TextView logSumHours = view1.findViewById(R.id.logAccTime);

                final EditText logDate = view1.findViewById(R.id.chooseDateLog);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener(){
                            public void onDateSet(DatePicker view, int year, int month,int day){
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH,month);
                                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                                logDate.setText(dateFormat.format(myCalendar.getTime()));
                            }
                        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE));

                logDate.setOnClickListener(view2 -> datePickerDialog.show());

                // Dropdown list Values
                Spinner logPriority = (Spinner) view1.findViewById(R.id.logPriority);
                ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.priority));
                priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                logPriority.setAdapter(priorityAdapter);

                Spinner logStatus = (Spinner) view1.findViewById(R.id.logStatus);
                ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.status));
                statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                logStatus.setAdapter(statusAdapter);


                Spinner logAssigned = (Spinner) view1.findViewById(R.id.logAssigned);

                ArrayAdapter<String> assignAdapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, membersList);
                mTaskViewModel.getAllTeamMembers().observe((LifecycleOwner) context, new Observer<List<Members>>() {
                    @Override
                    public void onChanged(@Nullable final List<Members> member) {
                        if (membersList.size() == 0) {
                            membersList.add("None");
                        }
                        for (int i = 0; i < member.size(); i++) {
                            if (!membersList.contains(member.get(i).getMemberName())) {
                                membersList.add(member.get(i).getMemberName());
                            }
                        }
                        logAssigned.setSelection(assignAdapter.getPosition(taskListRecycle.get(fPosition).getAssigned()));
                        assignAdapter.notifyDataSetChanged();
                    }

                });
                assignAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                logAssigned.setAdapter(assignAdapter);

                Spinner logTag = (Spinner) view1.findViewById(R.id.logTag);
                ArrayAdapter<String> tagAdapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.tags));
                tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                logTag.setAdapter(tagAdapter);

                RadioGroup editCategory = (RadioGroup) view1.findViewById(R.id.logCategory);

//                set selected task values
                String Category = taskListRecycle.get(fPosition).getCategory();
                if (Category.equals("User Story")) {
                    editCategory.check(R.id.userStoryLog);
                }
                else if (Category.equals("Bug")) {
                    editCategory.check(R.id.bugLog);
                }

                logName.setText(taskListRecycle.get(fPosition).getName());
                logPriority.setSelection(priorityAdapter.getPosition(taskListRecycle.get(fPosition).getPriority()));
                logStatus.setSelection(statusAdapter.getPosition(taskListRecycle.get(fPosition).getStatus()));
                logAssigned.setSelection(assignAdapter.getPosition(taskListRecycle.get(fPosition).getAssigned()));
                logTag.setSelection(tagAdapter.getPosition(taskListRecycle.get(fPosition).getTag()));
                logSP.setText(String.valueOf(taskListRecycle.get(fPosition).getStoryPoints()));
                logDesc.setText(taskListRecycle.get(fPosition).getDescription());

                executorService.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                logSumHours.setText("Accumulated Time: " + mTaskViewModel.getTaskHoursSum(taskListRecycle.get(fPosition).getTaskId()));
                                            }
                                        }
                );
                ExecutorService executorService = Executors.newFixedThreadPool(4);

                int id = taskListRecycle.get(fPosition).getTaskId();


//                editCategory.setEnabled(false);
//                logName.setEnabled(false);
//                logPriority.setEnabled(false);
//                logStatus.setEnabled(false);
//                logAssigned.setEnabled(false);
//                logTag.setEnabled(false);
//                logSP.setEnabled(false);
//                logDesc.setEnabled(false);
//
//                Button editButton = view1.findViewById(R.id.editButton);
//                editButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        editCategory.setEnabled(true);
//                        logName.setEnabled(true);
//                        logPriority.setEnabled(true);
//                        logStatus.setEnabled(true);
//                        logAssigned.setEnabled(true);
//                        logTag.setEnabled(true);
//                        logSP.setEnabled(true);
//                        logDesc.setEnabled(true);
//                    }
//                });

                Button updateButton = view1.findViewById(R.id.updateButton);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Task task = taskListRecycle.get(fPosition).getTask();
                        RadioGroup eCategory = (RadioGroup) view1.findViewById(R.id.logCategory);
                        RadioButton selected = (RadioButton) view1.findViewById(eCategory.getCheckedRadioButtonId());
                        String category = selected.getText().toString();
                        String name = logName.getText().toString();
                        String priority = logPriority.getSelectedItem().toString();
                        String status = logStatus.getSelectedItem().toString();
                        String assigned = logAssigned.getSelectedItem().toString();
                        String tag = logTag.getSelectedItem().toString();
                        String sp = logSP.getText().toString();
                        String desc = (logDesc.getText().toString());
                        String inputDate = logDate.getText().toString();
                        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                        String hours = logHours.getText().toString();

                        if (name.isEmpty() | sp.isEmpty() | desc.isEmpty() | hours.isEmpty() | inputDate.isEmpty()) {
                            Toast.makeText(context.getApplicationContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    Date date = null;
                                    try {
                                        date = format.parse(inputDate);
                                        String newDateString = format.format(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                            mTaskViewModel.updateTask(id, category, name, desc, priority, status, assigned, tag, Integer.parseInt(sp));
                            mTaskViewModel.insertDateHour(new Log_Task(id, assigned, mTaskViewModel.getAssignedMemberID(assigned), date, Integer.parseInt(hours)));
                            // reset fields after creating a task
                            logHours.setText("");
                            alertDialog.dismiss();
                        }

                    });
                            Toast.makeText(context.getApplicationContext(),
                                    "Task successfully updated and logged.", Toast.LENGTH_SHORT).show();
                }
                    }
                });

            }
        });


    }


    @Override
    public int getItemCount() {
        return taskListRecycle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView sprintTaskTitle;
        public Button sprintTaskStatus;

        public ViewHolder(View itemView){
            super(itemView);
            sprintTaskTitle = itemView.findViewById(R.id.sprint_task_title);
            sprintTaskStatus = itemView.findViewById(R.id.sprint_task_status);
        }
    }
}


