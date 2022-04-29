package se.mau.aj9191.assignment_2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Transaction.class}, version = 1, exportSchema = false)
public abstract class TransactionDatabase extends RoomDatabase
{
    public abstract TransactionDao transactionDao();

    private static volatile TransactionDatabase INSTANCE;

    public static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static TransactionDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (TransactionDatabase.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TransactionDatabase.class, "transaction_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
