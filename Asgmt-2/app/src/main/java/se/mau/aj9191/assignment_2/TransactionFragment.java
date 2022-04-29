package se.mau.aj9191.assignment_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class TransactionFragment extends Fragment implements Toolbar.OnMenuItemClickListener
{
    private Toolbar toolbar;
    private String transactionType;

    public TransactionFragment(String transactionType)
    {
        this.transactionType = transactionType;
    }

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        if (savedInstance != null)
            transactionType = savedInstance.getString("TransactionType");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance)
    {
        super.onSaveInstanceState(savedInstance);
        savedInstance.putString(transactionType, "TransactionType");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance)
    {
        super.onViewCreated(view, savedInstance);

        initializeComponents(view);
        registerListeners();
        addObservers();
    }

    private void initializeComponents(View view)
    {
        toolbar = view.findViewById(R.id.toolbar);

        toolbar.setTitle(transactionType);
    }
    private void registerListeners()
    {
        toolbar.setOnMenuItemClickListener(this);
    }
    private void addObservers()
    {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.btnTimePeriod:

                break;
        }
        return true;
    }
}
