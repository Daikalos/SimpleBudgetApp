package se.mau.aj9191.assignment_2;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;

public class TransactionAddDialog extends DialogFragment implements Toolbar.OnMenuItemClickListener
{
    public static final String Tag = "transaction_add_dialog";

    private static final String AddTransactionType = "AddTransactionType";
    private static final String SelectedType = "SelectedType";
    private static final String SelectedCategory = "SelectedCategory";

    private TransactionViewModel transactionViewModel;

    private String transactionType;
    private int selectedType;
    private int selectedCategory;

    private Toolbar toolbar;
    private EditText edTitle;
    private EditText edAmount;
    private Spinner spnrCategory;
    private DatePicker datePicker;

    public TransactionAddDialog() { }
    public TransactionAddDialog(String type)
    {
        this.transactionType = type;
    }

    public static void display(String type, FragmentManager fragmentManager)
    {
        TransactionAddDialog createTransactionDialog = new TransactionAddDialog(type);
        createTransactionDialog.show(fragmentManager, Tag);
    }

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.AddDialog);
        transactionViewModel = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class);

        if (savedInstance != null)
        {
            transactionType = savedInstance.getString(AddTransactionType);
            selectedType = savedInstance.getInt(SelectedType);
            selectedCategory = savedInstance.getInt(SelectedCategory);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstance)
    {
        final Dialog dialog = super.onCreateDialog(savedInstance);

        dialog.getWindow().getAttributes().windowAnimations = R.style.WindowSlide;

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_transaction_add, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance)
    {
        savedInstance.putString(AddTransactionType, transactionType);
        savedInstance.putInt(SelectedType, selectedType);
        savedInstance.putInt(SelectedCategory, selectedCategory);

        super.onSaveInstanceState(savedInstance);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstance)
    {
        super.onViewCreated(view, savedInstance);

        initializeComponents(view);
        registerListeners();

        if (savedInstance != null)
        {
            spnrCategory.setSelection(selectedCategory);
        }
    }

    private void initializeComponents(View view)
    {
        toolbar = view.findViewById(R.id.toolbar);
        edTitle = view.findViewById(R.id.etAddTitle);
        edAmount = view.findViewById(R.id.etAddAmount);
        spnrCategory = view.findViewById(R.id.spnrCategory);
        datePicker = view.findViewById(R.id.dpAdd);

        toolbar.setTitle("Add " + transactionType);
        spnrCategory.setAdapter(new ArrayAdapter<>(requireContext(),
                R.layout.custom_spinner_item, TransactionCategories.getCategories(transactionType)));
    }
    private void registerListeners()
    {
        toolbar.setNavigationOnClickListener(view -> dismiss());
        toolbar.setOnMenuItemClickListener(this);

        spnrCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedType = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        spnrCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedCategory = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        if (item.getItemId() == R.id.btnAdd)
        {
            String title = edTitle.getText().toString();
            String amount = edAmount.getText().toString();

            if (title.isEmpty())
            {
                ShowToast.show(requireContext(), getResources().getString(R.string.error_empty_title));
                return false;
            }
            if (amount.isEmpty())
            {
                ShowToast.show(requireContext(), getResources().getString(R.string.error_empty_amount));
                return false;
            }

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            transactionViewModel.insert(new TransactionEntity(new Transaction(transactionType, spnrCategory.getSelectedItem().toString(),
                    title, DateConverter.fromTimestamp(DateConverter.convert(year, month, day)), Integer.parseInt(amount))));

            dismiss();
        }
        return true;
    }
}
