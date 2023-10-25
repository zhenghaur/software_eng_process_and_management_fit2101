package com.example.software.provider;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "teamMembers")
public class Members {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name= "memberID")
    private int memberID;

    @ColumnInfo(name= "memberName")
    private String memberName;

    @ColumnInfo(name= "memberEmail")
    private String memberEmail;

    public Members(String memberName, String memberEmail) {
        this.memberName = memberName;
        this.memberEmail = memberEmail;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }
}
