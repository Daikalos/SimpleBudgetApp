package se.mau.aj9191.assignment_2;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BarcodeDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BarcodeEntity barcode);

    @Query("SELECT * FROM barcode_table WHERE barcode = :barcode")
    BarcodeEntity find(@NonNull String barcode);
}
