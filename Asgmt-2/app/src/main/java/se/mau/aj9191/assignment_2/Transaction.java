package se.mau.aj9191.assignment_2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "transaction_table")
public class Transaction
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @NonNull
    @ColumnInfo(name = "date")
    private String date;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "category")
    private String category;

    @NonNull
    @ColumnInfo(name = "amount")
    private String amount;

    @NonNull
    @ColumnInfo(name = "type")
    private String type;

    public Transaction(@NonNull String date,
                       @NonNull String title,
                       @NonNull String category,
                       @NonNull String amount,
                       @NonNull String type)
    {
        id = UUID.randomUUID().toString();

        this.date = date;
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.type = type;
    }
}
