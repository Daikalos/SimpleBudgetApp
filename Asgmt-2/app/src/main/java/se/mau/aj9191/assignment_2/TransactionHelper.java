package se.mau.aj9191.assignment_2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TransactionHelper
{
    public static void create(Context context, TransactionViewModel viewModel)
    {

    }
    public static void betweenDates(Context context, TransactionViewModel viewModel)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);

        View datePickerView = LayoutInflater.from(context).inflate(R.layout.date_picker, null);
        builder.setView(datePickerView);

        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("OK", (dialogInterface, i) ->
        {

        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
