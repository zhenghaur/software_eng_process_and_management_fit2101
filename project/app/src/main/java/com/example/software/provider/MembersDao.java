package com.example.software.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MembersDao {

    @Query("select * from teamMembers")
    LiveData<List<Members>> getAllTeamMembers();

    @Query("select * from teamMembers where memberName =:memberName")
    LiveData<List<Members>> getMemberName(String memberName);

    @Query("select * from teamMembers where memberEmail =:memberEmail")
    LiveData<List<Members>> getMemberEmail(String memberEmail);

    @Query("select * from teamMembers where memberName =:memberName")
    int getAssignedMemberID(String memberName);

    @Insert
    void addTeamMember(Members member);

    @Query("delete from teamMembers where memberID=:memberID")
    void deleteTeamMemberByID(int memberID);

    @Query("delete from teamMembers where memberName = :memberName")
    void deleteTeamMember(String memberName);

    @Query("delete FROM teamMembers")
    void deleteAllTeamMembers();

    @Query("UPDATE teamMembers SET memberName =:memberName, memberEmail =:memberEmail WHERE memberID =:memberID")
    void updateTeamMembers(int memberID, String memberName, String memberEmail);
}
