package com.example.software.provider;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.util.List;

public class TaskDateTime {

    @Embedded
    public Task task;
    @Relation(
            parentColumn = "taskId",
            entityColumn = "logId",
            entity = Log_Task.class
    )
    public Log_Task log_tasks;

//    @Ignore
//    public TaskDateTime(Task task, Log_Task log_task) {
//        this.task = task;
//        this.log_tasks = log_task;
//    }
}


//taskdatetime