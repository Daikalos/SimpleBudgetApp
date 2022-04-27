package se.mau.aj9191.assignment_2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionRepository
{
    private TransactionDao transactionDao;
    private LiveData<List<Transaction>> transactions;

    public TransactionRepository(Application application)
    {
        TransactionDatabase tdb = TransactionDatabase.getDatabase(application);
        transactionDao = tdb.transactionDao();
        transactions = transactionDao.getAllTransactions();
    }

    LiveData<List<Transaction>> getAllTransactions()
    {
        return transactions;
    }

    public void insert(Transaction transaction)
    {
        TransactionDatabase.executorService.execute(() -> transactionDao.insert(transaction));
    }
}
