package com.wasem.mysteriousquestions.DataBase;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public Long fromDate(Date date){
        if (date != null)
            return date.getTime();
        else
            return null;
    }
    @TypeConverter
    public Date fromDate(Long millis){
        if (millis != null)
            return new Date(millis);
        else
            return null;
    }
}
