package se.mau.aj9191.assignment_2;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BarcodeScanDialog extends DialogFragment
{
    public static final String Tag = "barcode_scan_dialog";

    private TransactionViewModel transactionViewModel;

    private Toolbar toolbar;

    private BarcodeScanner barcodeScanner;
    private BarcodeScannerOptions barcodeScannerOptions = new BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                    Barcode.FORMAT_CODABAR, Barcode.FORMAT_CODE_39,
                    Barcode.FORMAT_CODE_128, Barcode.FORMAT_UPC_A,
                    Barcode.FORMAT_EAN_13, Barcode.FORMAT_EAN_8,
                    Barcode.FORMAT_CODE_93).build();

    public static void display(FragmentManager fragmentManager)
    {
        TransactionAddDialog createTransactionDialog = new TransactionAddDialog();
        createTransactionDialog.show(fragmentManager, Tag);
    }

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.FragmentDialog);
        transactionViewModel = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class);
        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions);

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
        {
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), result ->
            {
                if (!result)
                {
                    ShowToast.show(requireContext(), getResources().getString(R.string.error_permissions));
                    dismiss();
                }
                else
                    scanBarcode();
            }).launch(Manifest.permission.CAMERA);
        }
        else
            scanBarcode();
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

        super.onSaveInstanceState(savedInstance);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstance)
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
        toolbar.setNavigationOnClickListener(view -> dismiss());
    }

    private void scanBarcode()
    {
        ListenableFuture<ProcessCameraProvider> instance = ProcessCameraProvider.getInstance(requireContext());
        instance.addListener(() ->
                {
                    try
                    {
                        ProcessCameraProvider cameraProvider = instance.get();

                        Preview preview = new Preview.Builder().build();

                    }
                    catch (ExecutionException e)
                    {
                        e.printStackTrace();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                },
                ContextCompat.getMainExecutor(requireContext()));
    }

    private class ImageAnalyzer implements ImageAnalysis.Analyzer
    {
        @Override
        @androidx.camera.core.ExperimentalGetImage
        public void analyze(@NonNull ImageProxy imageProxy)
        {
            Image mediaImage = imageProxy.getImage();
            if (mediaImage != null)
            {
                InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());

                Task<List<Barcode>> result = barcodeScanner.process(image).addOnSuccessListener(barcodes ->
                {
                    for (Barcode barcode : barcodes)
                    {
                        String rawValue = barcode.getRawValue();

                        int valueType = barcode.getValueType();
                        if (valueType == Barcode.TYPE_PRODUCT)
                        {

                        }
                    }
                })
                        .addOnFailureListener(e ->
                        {
                            e.printStackTrace();
                            Log.d("error", e.getMessage());
                        });
            }
        }
    }
}
