package se.mau.aj9191.assignment_2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Future;

public class TransactionRepository
{
    private final TransactionDao transactionDao;
    private final BarcodeDao barcodeDao;

    private LiveData<List<TransactionEntity>> allTransactions;
    private LiveData<List<TransactionEntity>> allExpenses;
    private LiveData<List<TransactionEntity>> allIncome;

    private SingleLiveEvent<List<TransactionEntity>> period = new SingleLiveEvent<>();

    private SingleLiveEvent<BarcodeEntity> barcodeEntity = new SingleLiveEvent<>();

    public TransactionRepository(Application application)
    {
        TransactionDatabase tdb = TransactionDatabase.getDatabase(application);
        transactionDao = tdb.transactionDao();
        barcodeDao = tdb.barcodeDao();

        allTransactions = transactionDao.getAll();
        allExpenses = transactionDao.getByType(TransactionType.Expenses);
        allIncome = transactionDao.getByType(TransactionType.Income);
    }

    public void insert(TransactionEntity transaction)
    {
        TransactionDatabase.executorService.execute(() -> transactionDao.insert(transaction));
    }
    public void delete(int id)
    {
        TransactionDatabase.executorService.execute(() -> transactionDao.delete(id));
    }

    LiveData<List<TransactionEntity>> getAll()
    {
        return allTransactions;
    }
    LiveData<List<TransactionEntity>> getByType(String type)
    {
        switch (type)
        {
            case TransactionType.Expenses:
                return allExpenses;
            case TransactionType.Income:
                return allIncome;
        }
        return transactionDao.getByType(type);
    }

    public void selectPeriod(String type, String since, String until)
    {
        TransactionDatabase.executorService.execute(() ->
                period.postValue(transactionDao.getBetweenDates(type, since, until)));
    }
    LiveData<List<TransactionEntity>> getPeriod()
    {
        return period;
    }

    public void findBarcode(@NonNull String barcode)
    {
        TransactionDatabase.executorService.execute(() ->
                barcodeEntity.postValue(barcodeDao.find(barcode)));
    }
    public LiveData<BarcodeEntity> getBarcode()
    {
        return barcodeEntity;
    }
}
