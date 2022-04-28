package se.mau.aj9191.assignment_2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel
{
    private TransactionRepository repository;

    private final LiveData<List<Transaction>> transactions;

    public TransactionViewModel(Application application)
    {
        super(application);

        repository = new TransactionRepository(application);
        transactions = repository.getAllTransactions();
    }

    LiveData<List<Transaction>> getTransactions()
    {
        return transactions;
    }

    public void insert(Transaction transaction)
    {
        repository.insert(transaction);
    }
    public void deleteByID(String id) { repository.deleteByID(id); }
}
