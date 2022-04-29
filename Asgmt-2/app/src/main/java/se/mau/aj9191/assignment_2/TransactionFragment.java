package se.mau.aj9191.assignment_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TransactionFragment extends Fragment implements Toolbar.OnMenuItemClickListener
{
    private TransactionViewModel transactionViewModel;
    private String transactionType;

    private Toolbar toolbar;
    private FloatingActionButton btnAction;
    private RecyclerView rvTransactions;
    private ListAdapter laTransactions;

    public TransactionFragment()
    {

    }
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
        savedInstance.putString("TransactionType", transactionType);
        super.onSaveInstanceState(savedInstance);
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
        btnAction = view.findViewById(R.id.fabAction);
        rvTransactions = view.findViewById(R.id.rvTransactions);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        rvTransactions.setLayoutManager(linearLayoutManager);
        rvTransactions.addItemDecoration(new DividerItemDecoration(requireContext(), linearLayoutManager.getOrientation()));
        rvTransactions.setAdapter(laTransactions = new TransactionAdapter(new TransactionAdapter.TransactionDiff(), requireContext(), transactionViewModel));

        toolbar.setTitle(transactionType);
        toolbar.setNavigationIcon(TransactionType.getIconFromType(requireContext(), transactionType));
    }
    private void registerListeners()
    {
        toolbar.setOnMenuItemClickListener(this);
        btnAction.setOnClickListener(view ->
        {
            transactionViewModel.insert(new Transaction(TransactionType.Expenditure, TransactionCategories.Food, "Hamburger", DateConverter.fromTimestamp("2020-01-12"), 100));
            rvTransactions.smoothScrollToPosition(rvTransactions.getTop());
        });
    }
    private void addObservers()
    {
        transactionViewModel.getByType(transactionType).observe(getViewLifecycleOwner(), transactions ->
                laTransactions.submitList(transactions));
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
