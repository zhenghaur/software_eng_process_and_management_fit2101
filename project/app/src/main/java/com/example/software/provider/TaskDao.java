package com.example.software.provider;

import static androidx.room.OnConflictStrategy.REPLACE;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface TaskDao {
    @Query("select * from tasks")
    LiveData<List<Task>> getAllTask();

//    @Query("select taskAssigned from tasks")
//    LiveData<List<Task>> getAllAvailableAssignedMembers();

    @Query("select * from tasks where taskSprint = :sprint")
    LiveData<List<Task>> getSprintTask(String sprint);

    @Query("select * from tasks where taskStatus = :status AND taskSprint = :sprint")
    LiveData<List<Task>> getSprintStatus(String sprint, String status);

    @Query("select * from tasks where taskSprint = :sprint AND (taskStatus = :status1 OR taskStatus = :status2 OR taskStatus = :status3)")
    LiveData<List<Task>> getSprintStatus2(String sprint, String status1, String status2, String status3);

    @Query("select sum(taskHours) from log_task where taskIdFK = :taskIdFK ")
    int getTaskHoursSum(int taskIdFK);

    @Query("select sum(taskHours) from log_task where (taskDate between :fromDate and :untilDate) and taskAssignedFK = :member")
    int getHoursBetweenDates(Date fromDate, Date untilDate, String member);


    @Transaction
    @Insert(onConflict = REPLACE)
    long addTask(Task task);

    @Insert(onConflict = REPLACE)
    long addTaskDateHours(Log_Task log_task);

    @Insert(onConflict = REPLACE)
    void addLogTask(List<Log_Task> log_tasks);

    @Query("select sum(taskHours) from log_task where assignedMemberID = :memberID AND taskDate = :date")
    int getDurationByMemberId(int memberID, Date date);

    @Query("delete from tasks where taskId= :id")
    void deleteById(int id);

    @Query("delete FROM tasks")
    void deleteAllTasks();

    @Query("UPDATE tasks SET taskSprint = 'PB' WHERE taskSprint = :sprint AND NOT taskStatus = 'Completed'")
    void endSprint(String sprint);

    @Query("UPDATE tasks SET taskSprint = :sprint WHERE taskId = :id")
    void updateSprint(int id, String sprint);

    @Query("UPDATE tasks SET taskCategory = :category, taskName = :name, taskDescription = :description," +
            " taskPriority = :priority, taskStatus = :status, taskAssigned = :assigned, taskTag = :tag," +
            " taskStoryPoints = :storyPoints WHERE taskId = :id")

    void updateTask(int id, String category, String name, String description, String priority, String status, String assigned, String tag, int storyPoints);



}
