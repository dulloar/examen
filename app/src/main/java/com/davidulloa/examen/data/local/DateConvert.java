package com.davidulloa.examen.data.local;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConvert {
    @TypeConverter
    public long toLong(Date fecha){
        if(fecha == null){
            return 0;
        }
        return fecha.getTime();
    }

    @TypeConverter
    public Date toDate(long date){
        return (date == 0)?null: new Date(date);
    }
}
