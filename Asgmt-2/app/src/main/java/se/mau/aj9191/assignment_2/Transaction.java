package se.mau.aj9191.assignment_2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "transaction_table")
public class Transaction
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "date")
    private final String date;

    @NonNull
    @ColumnInfo(name = "title")
    private final String title;

    @NonNull
    @ColumnInfo(name = "category")
    private final String category;

    @ColumnInfo(name = "amount")
    private final int amount;

    @NonNull
    @ColumnInfo(name = "type")
    private final String type;

    public Transaction(@NonNull String date,
                       @NonNull String title,
                       @NonNull String category,
                       @NonNull int amount,
                       @NonNull String type)
    {
        this.date = date;
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.type = type;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }
    public String getTitle()
    {
        return title;
    }
    public String getCategory()
    {
        return category;
    }
    public int getAmount()
    {
        return amount;
    }
    public String getType()
    {
        return type;
    }
}
