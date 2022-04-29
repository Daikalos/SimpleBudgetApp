package se.mau.aj9191.assignment_2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel
{
    private TransactionRepository repository;

    public TransactionViewModel(Application application)
    {
        super(application);
        repository = new TransactionRepository(application);
    }

    public void insert(Transaction transaction)
    {
        repository.insert(transaction);
    }
    public void delete(String id) { repository.delete(id); }

    public LiveData<List<Transaction>> getAll()
    {
        return repository.getAll();
    }
    public LiveData<List<Transaction>> getByType(String type)
    {
        return repository.getByType(type);
    }
    public LiveData<List<Transaction>> getBetweenDates(String type, String from, String to)
    {
        return repository.getBetweenDates(type, from, to);
    }
}
