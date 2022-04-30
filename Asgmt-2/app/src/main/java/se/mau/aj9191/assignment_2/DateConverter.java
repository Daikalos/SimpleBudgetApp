package se.mau.aj9191.assignment_2;

import android.util.Log;

import androidx.room.Ignore;
import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class DateConverter
{
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat mdf = new SimpleDateFormat("MMM dd yyyy");

    @TypeConverter
    public static Date fromTimestamp(String value)
    {
        if (value != null)
        {
            try
            {
                return df.parse(value);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        return null;
    }

    @TypeConverter
    public static String toTimestamp(Date date)
    {
        return df.format(date);
    }

    @Ignore
    public static String convert(int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return df.format(calendar.getTime());
    }

    @Ignore
    public static String format(Date value)
    {
        return mdf.format(value);
    }
}
