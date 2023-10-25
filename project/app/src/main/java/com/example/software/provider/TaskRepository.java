package com.example.software.provider;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

public class TaskRepository {
    private TaskDao mTaskDao;
    private SprintDao mSprintDao;
    private MembersDao mMembersDao;
    private LiveData<List<Sprint>> mAllSprints;
    private LiveData<List<Members>> mAllMembers;
    private LiveData<List<Task>> mAllTasks;
    private LiveData<List<Task>> mSprintTasks;

    TaskRepository(Application application) {
        TaskDatabase db = TaskDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        mSprintDao = db.sprintDao();
        mMembersDao = db.membersDao();
        mAllMembers = mMembersDao.getAllTeamMembers();
        mAllSprints = mSprintDao.getAllSprints();
        mAllTasks = mTaskDao.getAllTask();
    }

//    public void insert(TaskDateTime taskDateTime){
//        new insertAsync(mTaskDao).execute(taskDateTime);
//    }
//
//    private static class insertAsync extends AsyncTask<TaskDateTime, Void, Void>{
//        private TaskDao taskDaoAsync;
//
//        insertAsync(TaskDao taskDao){
//            taskDaoAsync = taskDao;
//        }
//
//        @Override
//        protected Void doInBackground(TaskDateTime... taskDateTimes) {
//            int identifier = (int) taskDaoAsync.addTask(taskDateTimes[0].task);
//
//            for (Log_Task log_task : taskDateTimes[0].log_tasks){
//                log_task.setTaskIdFK(identifier);
//            }
//            taskDaoAsync.addLogTask(taskDateTimes[0].log_tasks);
//            return null;
//        }
//    }

    //Task Repositories

    LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    LiveData<List<Task>> getSprintTasks(String sprint) {
        return mTaskDao.getSprintTask(sprint);
    }

    LiveData<List<Task>> getSprintStatus(String sprint, String status) {
        return mTaskDao.getSprintStatus(sprint, status);
    }

    LiveData<List<Task>> getSprintStatus2(String sprint, String status1, String status2, String status3) {
        return mTaskDao.getSprintStatus2(sprint, status1, status2, status3);
    }

//    LiveData<List<TaskDateTime>> getTaskDateHours() { return mAllTaskHours; }


    void insert(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> mTaskDao.addTask(task));
    }

    void insertDateHour(Log_Task log_task){
        TaskDatabase.databaseWriteExecutor.execute(()-> mTaskDao.addTaskDateHours(log_task));
    }


    void deleteById(int id){
        TaskDatabase.databaseWriteExecutor.execute(() -> mTaskDao.deleteById(id));
    }

    void deleteAll(){
        TaskDatabase.databaseWriteExecutor.execute(()->{
            mTaskDao.deleteAllTasks();
        });
    }

    void updateSprint(int id, String sprint){
        TaskDatabase.databaseWriteExecutor.execute(()->{
            mTaskDao.updateSprint(id, sprint);
        });
    }

    void endSprint(String sprint){
        TaskDatabase.databaseWriteExecutor.execute(()->{
            mTaskDao.endSprint(sprint);
        });
    }

    void updateTask(int id, String category, String name, String description, String priority,
                    String status, String assigned, String tag, int storyPoints){
        TaskDatabase.databaseWriteExecutor.execute(()->{
            mTaskDao.updateTask(id,category,name,description,priority,status,assigned,tag,
                    storyPoints);
        });
    }
    
    int getTaskHoursSum(int taskIdFK){
        return mTaskDao.getTaskHoursSum(taskIdFK);
    }

    int getHoursBetweenDates(Date fromDate, Date untilDate, String member) {
        return mTaskDao.getHoursBetweenDates(fromDate,untilDate,member);
    }
    //Sprint Repositories

    LiveData<List<Sprint>> getAllSprints(){
        return mAllSprints;
    }

    LiveData<List<Sprint>> getSprintName(String sprint) {
        return mSprintDao.getSprintName(sprint);
    }

    LiveData<List<Sprint>> getSprintStartDate(String startDate) {
        return mSprintDao.getSprintStartDate(startDate);
    }

    LiveData<List<Sprint>> getSprintEndDate(String endDate) {
        return mSprintDao.getSprintEndDate(endDate);
    }

    void addSprint(Sprint sprint) {
        TaskDatabase.databaseWriteExecutor.execute(() -> mSprintDao.addSprint(sprint));
    }

    void deleteSprintByID(int id) {
        TaskDatabase.databaseWriteExecutor.execute(() -> mSprintDao.deleteSprintByID(id));
    }

    void deleteSprintByName(String name){
        TaskDatabase.databaseWriteExecutor.execute(() -> mSprintDao.deleteSprintByName(name));
    }

    void deleteAllSprints(){
        TaskDatabase.databaseWriteExecutor.execute(()->{
            mSprintDao.deleteAllSprints();
        });
    }

    void updateSprintDetails(int id, String sprintName, String sprintStartDate, String sprintEndDate){
        TaskDatabase.databaseWriteExecutor.execute(()->{
            mSprintDao.updateSprintDetails(id, sprintName, sprintStartDate, sprintEndDate);
        });
    }

    //Members Repositories

    LiveData<List<Members>> getAllTeamMembers() {
        return mAllMembers;
    }

    LiveData<List<Members>> getMemberName(String memberName) {
        return mMembersDao.getMemberName(memberName);
    }

    LiveData<List<Members>> getMemberEmail(String memberEmail) {
        return mMembersDao.getMemberEmail(memberEmail);
    }

    void addTeamMember(Members member) {
        TaskDatabase.databaseWriteExecutor.execute(() -> mMembersDao.addTeamMember(member));
    }

    void deleteTeamMemberByID(int memberID) {
        TaskDatabase.databaseWriteExecutor.execute(() -> mMembersDao.deleteTeamMemberByID(memberID));
    }
    void deleteTeamMember(String memberName) {
        TaskDatabase.databaseWriteExecutor.execute(() ->mMembersDao.deleteTeamMember(memberName));
    }

    void deleteAllMembers() {
        TaskDatabase.databaseWriteExecutor.execute(() -> mMembersDao.deleteAllTeamMembers());
    }

    void updateTeamMembers(int id, String memberName, String memberEmail) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {mMembersDao.updateTeamMembers(id, memberName
                , memberEmail);
        });
    }
    int getAssignedMemberID(String memberName){
        return mMembersDao.getAssignedMemberID(memberName);
    }

    int getDurationByMemberID(int memberID, Date date){
        return mTaskDao.getDurationByMemberId(memberID, date);
    }
}


//task repo