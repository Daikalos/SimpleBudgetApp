package se.mau.aj9191.assignment_2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Future;

public class TransactionRepository
{
    private TransactionDao transactionDao;

    public TransactionRepository(Application application)
    {
        TransactionDatabase tdb = TransactionDatabase.getDatabase(application);
        transactionDao = tdb.transactionDao();
    }

    public void insert(Transaction transaction)
    {
        TransactionDatabase.executorService.execute(() -> transactionDao.insert(transaction));
    }
    public void delete(String id)
    {
        TransactionDatabase.executorService.execute(() -> transactionDao.delete(id));
    }

    LiveData<List<Transaction>> getAll()
    {
        return transactionDao.getAll();
    }
    LiveData<List<Transaction>> getByType(String type)
    {
        return transactionDao.getByType(type);
    }
    LiveData<List<Transaction>> getBetweenDates(String type, String from, String to)
    {
        return transactionDao.getBetweenDates(type, from, to);
    }
}
