package se.mau.aj9191.assignment_2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Transaction transaction);

    @Query("DELETE FROM TRANSACTION_TABLE")
    public void deleteAll();

    @Query("SELECT * FROM transaction_table WHERE type = :type")
    LiveData<List<Transaction>> findByType(String type);

    @Query("SELECT * FROM transaction_table")
    LiveData<List<Transaction>> getAllTransactions();
}
