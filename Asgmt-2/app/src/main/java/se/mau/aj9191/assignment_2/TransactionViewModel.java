package se.mau.aj9191.assignment_2;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel
{
    private TransactionRepository repository;

    private SingleLiveEvent<List<Transaction>> transactionsPeriod = new SingleLiveEvent<>();

    public TransactionViewModel(Application application)
    {
        super(application);
        repository = new TransactionRepository(application);
    }

    public void insert(Transaction transaction)
    {
        repository.insert(transaction);
    }
    public void delete(int id) { repository.delete(id); }

    public LiveData<List<Transaction>> getAll()
    {
        return repository.getAll();
    }
    public LiveData<List<Transaction>> getByType(String type)
    {
        return repository.getByType(type);
    }

    public void getBetweenDates(String type, String since, String until)
    {
        repository.getBetweenDates(type, since, until);
    }
    public LiveData<List<Transaction>> getPeriod()
    {
        return repository.getPeriod();
    }
}
