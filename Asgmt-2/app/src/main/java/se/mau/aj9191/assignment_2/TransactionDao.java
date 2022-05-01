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
    void insert(TransactionEntity transaction);

    @Query("DELETE FROM transaction_table WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM transaction_table")
    LiveData<List<TransactionEntity>> getAll();

    @Query("SELECT * FROM transaction_table WHERE type = :type ORDER BY date DESC")
    LiveData<List<TransactionEntity>> getByType(String type);

    @Query("SELECT * FROM transaction_table WHERE type = :type AND date BETWEEN :since AND :until ORDER BY date DESC")
    List<TransactionEntity> getBetweenDates(String type, String since, String until);
}
