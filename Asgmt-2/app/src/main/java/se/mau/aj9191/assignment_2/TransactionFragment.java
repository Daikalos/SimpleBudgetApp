package se.mau.aj9191.assignment_2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class TransactionFragment extends Fragment implements Toolbar.OnMenuItemClickListener
{
    private static final String Type = "TransactionType";
    private static final String SinceDate = "SinceDate";
    private static final String UntilDate = "UntilDate";

    private TransactionViewModel transactionViewModel;
    private String transactionType = "", sinceDate = "", untilDate = "";

    private Toolbar toolbar;
    private FloatingActionButton btnAction;
    private RecyclerView rvTransactions;
    private ListAdapter laTransactions;


    public TransactionFragment() { }
    public TransactionFragment(String transactionType)
    {
        this.transactionType = transactionType;
    }

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        transactionViewModel = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class);

        if (savedInstance != null)
        {
            transactionType = savedInstance.getString(Type);
            sinceDate = savedInstance.getString(SinceDate);
            untilDate = savedInstance.getString(UntilDate);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance)
    {
        savedInstance.putString(Type, transactionType);
        savedInstance.putString(SinceDate, sinceDate);
        savedInstance.putString(UntilDate, untilDate);

        super.onSaveInstanceState(savedInstance);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance)
    {
        super.onViewCreated(view, savedInstance);

        initializeComponents(view);
        registerListeners();
        addObservers();

        if (savedInstance != null)
        {
            if (!sinceDate.isEmpty() && !untilDate.isEmpty())
                transactionViewModel.selectPeriod(transactionType, sinceDate, untilDate);
        }
    }

    private void initializeComponents(View view)
    {
        toolbar = view.findViewById(R.id.toolbar);
        btnAction = view.findViewById(R.id.fabAction);
        rvTransactions = view.findViewById(R.id.rvTransactions);

        rvTransactions.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvTransactions.setAdapter(laTransactions = new TransactionAdapter(new TransactionAdapter.TransactionDiff(), requireContext(), transactionViewModel));

        toolbar.setTitle(transactionType);
        toolbar.setNavigationIcon(TransactionType.getIconFromType(requireContext(), transactionType));

        if (!transactionType.equals(TransactionType.Expenses))
            toolbar.getMenu().removeItem(R.id.btnBarcode);
    }
    private void registerListeners()
    {
        toolbar.setOnMenuItemClickListener(this);
        btnAction.setOnClickListener(view ->
        {
            TransactionAddDialog.display(transactionType, null, getChildFragmentManager());
            rvTransactions.smoothScrollToPosition(rvTransactions.getTop());
        });
    }
    private void addObservers()
    {
        transactionViewModel.getByType(transactionType).observe(getViewLifecycleOwner(),
                transactions -> laTransactions.submitList(transactions));
        transactionViewModel.getPeriod().observe(getViewLifecycleOwner(),
                transactions -> laTransactions.submitList(transactions));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.btnTimePeriod:
                setPeriod();
                break;
            case R.id.btnUndo:
                undo();
                break;
            case R.id.btnBarcode:
                BarcodeScanDialog.display(getChildFragmentManager());
                break;
        }
        return true;
    }

    private void setPeriod()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.CustomDialog);

        View datePickerView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_date_period, null);
        builder.setView(datePickerView);

        DatePicker datePickerSince = datePickerView.findViewById(R.id.dpSince);
        DatePicker datePickerUntil = datePickerView.findViewById(R.id.dpUntil);

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setPositiveButton("OK", (dialogInterface, i) ->
        {
            int sinceDay = datePickerSince.getDayOfMonth();
            int sinceMonth = datePickerSince.getMonth();
            int sinceYear = datePickerSince.getYear();

            int untilDay = datePickerUntil.getDayOfMonth();
            int untilMonth = datePickerUntil.getMonth();
            int untilYear = datePickerUntil.getYear();

            transactionViewModel.selectPeriod(transactionType,
                    sinceDate = DateConverter.convert(sinceYear, sinceMonth, sinceDay),
                    untilDate = DateConverter.convert(untilYear, untilMonth, untilDay));

            dialogInterface.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void undo()
    {
        sinceDate = untilDate = "";
        laTransactions.submitList(transactionViewModel.getByType(transactionType).getValue());
    }
}
