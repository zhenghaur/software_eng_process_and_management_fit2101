package com.example.software.provider;

import androidx.room.TypeConverter;

import java.util.Date;


public class DateConverter {
    @TypeConverter
    public static Date fromTimeStamp(Long stringdate){
        return stringdate == null ? null : new Date(stringdate);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null? null : date.getTime();
    }
}