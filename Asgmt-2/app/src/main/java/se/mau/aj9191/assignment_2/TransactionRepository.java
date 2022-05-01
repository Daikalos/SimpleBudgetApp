package se.mau.aj9191.assignment_2;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Future;

public class TransactionRepository
{
    private TransactionDao transactionDao;

    private LiveData<List<Transaction>> allTransactions;
    private LiveData<List<Transaction>> allExpenses;
    private LiveData<List<Transaction>> allIncome;

    private SingleLiveEvent<List<Transaction>> period = new SingleLiveEvent<>();

    public TransactionRepository(Application application)
    {
        TransactionDatabase tdb = TransactionDatabase.getDatabase(application);
        transactionDao = tdb.transactionDao();

        allTransactions = transactionDao.getAll();
        allExpenses = transactionDao.getByType(TransactionType.Expenses);
        allIncome = transactionDao.getByType(TransactionType.Income);
    }

    public void insert(Transaction transaction)
    {
        TransactionDatabase.executorService.execute(() -> transactionDao.insert(transaction));
    }
    public void delete(int id)
    {
        TransactionDatabase.executorService.execute(() -> transactionDao.delete(id));
    }

    LiveData<List<Transaction>> getAll()
    {
        return allTransactions;
    }
    LiveData<List<Transaction>> getByType(String type)
    {
        switch (type)
        {
            case TransactionType.Expenses:
                return allExpenses;
            case TransactionType.Income:
                return allIncome;
            case TransactionType.All:
                return allTransactions;
        }
        return transactionDao.getByType(type);
    }

    public void getBetweenDates(String type, String since, String until)
    {
        TransactionDatabase.executorService.execute(() ->
                period.postValue(transactionDao.getBetweenDates(type, since, until)));
    }
    LiveData<List<Transaction>> getPeriod()
    {
        return period;
    }
}
