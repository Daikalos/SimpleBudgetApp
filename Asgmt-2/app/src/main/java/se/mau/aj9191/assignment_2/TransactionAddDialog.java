package se.mau.aj9191.assignment_2;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

public class TransactionAddDialog extends DialogFragment implements View.OnClickListener
{
    public static final String Tag = "transaction_add_dialog";

    private TransactionViewModel transactionViewModel;
    private Toolbar toolbar;

    public static TransactionAddDialog display(FragmentManager fragmentManager)
    {
        TransactionAddDialog createTransactionDialog = new TransactionAddDialog();
        createTransactionDialog.show(fragmentManager, Tag);
        return createTransactionDialog;
    }

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AddDialog);

        transactionViewModel = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class);
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
        return inflater.inflate(R.layout.fragment_add_transaction, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance)
    {
        super.onViewCreated(view, savedInstance);

        initializeComponents(view);
        registerListeners();
    }

    private void initializeComponents(View view)
    {
        toolbar = view.findViewById(R.id.toolbar);
    }
    private void registerListeners()
    {
        toolbar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        dismiss();
    }
}
