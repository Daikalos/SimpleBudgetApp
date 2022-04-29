package se.mau.aj9191.assignment_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class OverviewFragment extends Fragment implements Toolbar.OnMenuItemClickListener
{
    private Toolbar toolbar;

    private PrefsViewModel prefsViewModel;
    private TransactionViewModel transactionViewModel;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
        prefsViewModel = viewModelProvider.get(PrefsViewModel.class);
        transactionViewModel = viewModelProvider.get(TransactionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
    {
        return inflater.inflate(R.layout.fragment_overview, container, false);
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

        toolbar.setTitle("Welcome " + prefsViewModel.getFirstName() + " " + prefsViewModel.getSurname());
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
            case R.id.btnChangeName:

                break;
        }
        return true;
    }


}
