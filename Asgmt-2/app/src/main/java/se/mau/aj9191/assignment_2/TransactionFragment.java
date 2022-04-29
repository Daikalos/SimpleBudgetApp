package se.mau.aj9191.assignment_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TransactionFragment extends Fragment implements Toolbar.OnMenuItemClickListener
{
    private Toolbar toolbar;
    private FloatingActionButton actionButton;
    private RecyclerView rvTransactions;

    private String transactionType;

    private TransactionViewModel transactionViewModel;

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

        transactionViewModel = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class);
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
        actionButton = view.findViewById(R.id.fabAction);
        rvTransactions = view.findViewById(R.id.rvTransactions);

        rvTransactions.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvTransactions.setAdapter(new TransactionAdapter(new TransactionAdapter.TransactionDiff()));

        toolbar.setTitle(transactionType);
        toolbar.setNavigationIcon(TransactionType.getIconFromType(requireContext(), transactionType));
    }
    private void registerListeners()
    {
        toolbar.setOnMenuItemClickListener(this);
        actionButton.setOnClickListener(view ->
        {

        });
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
