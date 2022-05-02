package se.mau.aj9191.assignment_2;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel
{
    private TransactionRepository repository;

    public TransactionViewModel(Application application)
    {
        super(application);
        repository = new TransactionRepository(application);
    }

    public void insert(TransactionEntity transaction)
    {
        repository.insert(transaction);
    }
    public void delete(int id) { repository.delete(id); }

    public LiveData<List<TransactionEntity>> getAll()
    {
        return repository.getAll();
    }
    public LiveData<List<TransactionEntity>> getByType(String type)
    {
        return repository.getByType(type);
    }

    public void selectPeriod(String type, String since, String until)
    {
        repository.selectPeriod(type, since, until);
    }
    public LiveData<List<TransactionEntity>> getPeriod()
    {
        return repository.getPeriod();
    }

    public void findBarcode(@NonNull String barcode)
    {
        repository.findBarcode(barcode);
    }
    public LiveData<BarcodeEntity> getBarcode()
    {
        return repository.getBarcode();
    }
}
