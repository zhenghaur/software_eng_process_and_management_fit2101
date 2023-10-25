package com.example.software.provider;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName= "log_task")
//, indices = {@Index(value = {"logId"}, unique = true)},foreignKeys = @ForeignKey(entity = Task.class, parentColumns = "taskId",
//                childColumns = "taskIdFK",
//                onDelete = ForeignKey.CASCADE,
//                onUpdate = ForeignKey.CASCADE))

@TypeConverters(DateConverter.class)
public class Log_Task {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int logId;

    @ColumnInfo(name="taskIdFK", index= true)
    private int taskIdFK;

    private String taskAssignedFK;

    private int assignedMemberID;

    private Date taskDate;

    private int taskHours;



    public int getTaskIdFK() {
        return taskIdFK;
    }

    public int getTaskHours() {
        return taskHours;
    }

    public java.util.Date getTaskDate() {
        return taskDate;
    }

    public int getLogId() {
        return logId;
    }

    public Log_Task(int taskIdFK, String taskAssignedFK, int assignedMemberID, Date taskDate, int taskHours) {
        this.taskIdFK = taskIdFK;
        this.taskAssignedFK = taskAssignedFK;
        this.assignedMemberID = assignedMemberID;
        this.taskDate = taskDate;
        this.taskHours = taskHours;
    }

    public int getAssignedMemberID() {
        return assignedMemberID;
    }
    public String getTaskAssignedFK() {
        return taskAssignedFK;
    }
//
    public void setTaskAssignedFK(String taskAssignedFK) {
        this.taskAssignedFK = taskAssignedFK;
    }

    public void setAssignedMemberID(int assignedMemberID) {
        this.assignedMemberID = assignedMemberID;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public void setTaskIdFK(int taskIdFK) {
        this.taskIdFK = taskIdFK;
    }

    public void setTaskDate(Date date) {this.taskDate = date; }

    public void setTaskHours(int hours) {
        this.taskHours = hours;
    }

}

