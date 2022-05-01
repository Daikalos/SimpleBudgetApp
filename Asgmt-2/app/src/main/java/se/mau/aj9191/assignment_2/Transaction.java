package se.mau.aj9191.assignment_2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.UUID;

public class Transaction
{
    private final String type;
    private final String category;
    private final String title;
    @TypeConverters({DateConverter.class})
    private final Date date;
    private final int amount;

    public Transaction(@NonNull String type,
                       @NonNull String category,
                       @NonNull String title,
                       @NonNull Date date,
                       int amount)
    {
        this.type = type;
        this.category = category;
        this.title = title;
        this.date = date;
        this.amount = amount;
    }

    public String getType()
    {
        return type;
    }
    public String getCategory()
    {
        return category;
    }
    public String getTitle()
    {
        return title;
    }
    public Date getDate()
    {
        return date;
    }
    public int getAmount()
    {
        return amount;
    }

    public boolean equals(Transaction other)
    {
        return type.equals(other.getType()) &&
                category.equals(other.getCategory()) &&
                title.equals(other.getTitle()) &&
                date.equals(other.getDate()) &&
                amount == other.getAmount();
    }
}
