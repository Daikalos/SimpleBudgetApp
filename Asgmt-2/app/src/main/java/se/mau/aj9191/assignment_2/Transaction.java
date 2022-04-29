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
    @ColumnInfo(name = "type")
    private final String type;

    @NonNull
    @ColumnInfo(name = "category")
    private final String category;

    @NonNull
    @ColumnInfo(name = "title")
    private final String title;

    @NonNull
    @ColumnInfo(name = "date")
    private final String date;

    @ColumnInfo(name = "amount")
    private final int amount;

    public Transaction(@NonNull String type,
                       @NonNull String category,
                       @NonNull String title,
                       @NonNull String date,
                       @NonNull int amount)
    {
        this.type = type;
        this.category = category;
        this.title = title;
        this.date = date;
        this.amount = amount;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
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
    public String getDate()
    {
        return date;
    }
    public int getAmount()
    {
        return amount;
    }

    @Override
    public boolean equals(Object other)
    {
        Transaction transaction = (Transaction)other;

        if (transaction == null)
            return false;

        return type.equals(transaction.getType()) &&
                category.equals(transaction.getCategory()) &&
                title.equals(transaction.getTitle()) &&
                date.equals(transaction.getDate()) &&
                amount == transaction.getAmount();
    }
}
