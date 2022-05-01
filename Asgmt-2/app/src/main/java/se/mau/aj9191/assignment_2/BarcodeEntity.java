package se.mau.aj9191.assignment_2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "barcode_table")
public class BarcodeEntity
{
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "barcode")
    private final String barcode;

    @Embedded
    private final Transaction transaction;

    public BarcodeEntity(@NonNull String barcode, Transaction transaction)
    {
        this.barcode = barcode;
        this.transaction = transaction;
    }

    @NonNull
    public String getBarcode()
    {
        return barcode;
    }
    public Transaction getTransaction()
    {
        return transaction;
    }
}
