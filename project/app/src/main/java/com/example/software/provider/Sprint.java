package com.example.software.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sprints")
public class Sprint{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "sprintID")
    private int sprintID;

    @ColumnInfo(name = "sprintName")
    private String sprintName;

    @ColumnInfo(name = "sprintStartDate")
    private String sprintStartDate;

    @ColumnInfo(name = "sprintEndDate")
    private String sprintEndDate;

    public Sprint(String sprintName, String sprintStartDate, String sprintEndDate) {
        this.sprintName = sprintName;
        this.sprintStartDate = sprintStartDate;
        this.sprintEndDate = sprintEndDate;
    }

    public int getSprintID() {
        return sprintID;
    }

    public String getSprintName() {
        return sprintName;
    }

    public String getSprintStartDate() {
        return sprintStartDate;
    }

    public String getSprintEndDate() {
        return sprintEndDate;
    }

    public void setSprintID(int sprintID) {
        this.sprintID = sprintID;
    }

    public Sprint getSprintStuff() {
        return this;
    }
}
