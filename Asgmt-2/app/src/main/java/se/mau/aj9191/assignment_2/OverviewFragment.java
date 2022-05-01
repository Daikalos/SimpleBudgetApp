package se.mau.aj9191.assignment_2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;
import java.util.List;

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

        toolbar.setTitle(prefsViewModel.getFirstName() + " " + prefsViewModel.getSurname());
    }
    private void registerListeners()
    {
        toolbar.setOnMenuItemClickListener(this);
    }
    private void addObservers()
    {
        transactionViewModel.getAll().observe(getViewLifecycleOwner(), transactions ->
        {

        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.btnChangeName:
                changeName();
                break;
        }
        return true;
    }

    private void changeName()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.CustomDialog);

        View changeNameView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_change_name, null);
        builder.setView(changeNameView);

        EditText etFirstName = changeNameView.findViewById(R.id.etFirstName);
        EditText etSurname = changeNameView.findViewById(R.id.etSurname);

        builder.setNegativeButton(R.string.txt_cancel, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setPositiveButton("OK", (dialogInterface, i) ->
        {
            String firstName = etFirstName.getText().toString();
            String surname = etSurname.getText().toString();

            if (firstName.isEmpty())
            {
                ShowToast.show(requireContext(), "first name is empty");
                return;
            }
            if (surname.isEmpty())
            {
                ShowToast.show(requireContext(), "surname is empty");
                return;
            }

            prefsViewModel.setFirstName(etFirstName.getText().toString());
            prefsViewModel.setSurname(etSurname.getText().toString());

            toolbar.setTitle(prefsViewModel.getFirstName() + " " + prefsViewModel.getSurname());

            dialogInterface.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
