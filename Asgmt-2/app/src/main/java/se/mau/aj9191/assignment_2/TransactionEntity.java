package se.mau.aj9191.assignment_2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "transaction_table")
public class TransactionEntity
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @Embedded
    private final Transaction transaction;

    public TransactionEntity(@NonNull Transaction transaction)
    {
        this.transaction = transaction;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public Transaction getTransaction()
    {
        return transaction;
    }

    public String getType()
    {
        return transaction.getType();
    }
    public String getCategory()
    {
        return transaction.getCategory();
    }
    public String getTitle()
    {
        return transaction.getTitle();
    }
    public Date getDate()
    {
        return transaction.getDate();
    }
    public int getAmount()
    {
        return transaction.getAmount();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other.getClass() != TransactionEntity.class)
            return false;

        return transaction.equals(((TransactionEntity)other).getTransaction());
    }
}
