package se.mau.aj9191.assignment_2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultFillFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class OverviewFragment extends Fragment implements Toolbar.OnMenuItemClickListener
{
    private Toolbar toolbar;

    private PrefsViewModel prefsViewModel;
    private TransactionViewModel transactionViewModel;

    private TextView tvIncomeAmount;
    private TextView tvExpensesAmount;
    private TextView tvTotalAmount;

    private LineChart chartIncome;
    private PieChart chartExpenses;

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
        tvIncomeAmount = view.findViewById(R.id.tvIncomeAmount);
        tvExpensesAmount = view.findViewById(R.id.tvExpensesAmount);
        tvTotalAmount = view.findViewById(R.id.tvTotalAmount);
        chartIncome = view.findViewById(R.id.chartIncome);
        chartExpenses = view.findViewById(R.id.chartExpenses);

        toolbar.setTitle(prefsViewModel.getFirstName() + " " + prefsViewModel.getSurname());
    }
    private void registerListeners()
    {
        toolbar.setOnMenuItemClickListener(this);
    }
    private void addObservers()
    {
        transactionViewModel.getAll().observe(getViewLifecycleOwner(), this::calculateSummary);
        transactionViewModel.getByType(TransactionType.Income).observe(getViewLifecycleOwner(), this::addIncomeChart);
        transactionViewModel.getByType(TransactionType.Expenses).observe(getViewLifecycleOwner(), this::addExpensesChart);
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

    private void calculateSummary(List<TransactionEntity> transactions)
    {
        int income = 0;
        int expenses = 0;

        for (TransactionEntity transaction : transactions)
        {
            switch (transaction.getType())
            {
                case TransactionType.Income:
                    income += transaction.getAmount();
                    break;
                case TransactionType.Expenses:
                    expenses += transaction.getAmount();
                    break;
            }
        }

        if (expenses > 0)
            tvExpensesAmount.setText("-" + expenses + "$");

        tvIncomeAmount.setText(income + "$");
        tvTotalAmount.setText((income - expenses) + "$");
    }
    private void addIncomeChart(List<TransactionEntity> transactions)
    {
        HashMap<String, List<Entry>> entries = new HashMap<>();
        for (int i = transactions.size() - 1; i >= 0; --i)
        {
            TransactionEntity transaction = transactions.get(i);

            String category = transaction.getCategory();
            Date date = transaction.getDate();
            int amount = transaction.getAmount();

            if (!entries.containsKey(category))
                entries.put(category, new ArrayList<>());

            entries.get(category).add(new Entry(date.getTime(), amount));
        }

        LineData lineData = new LineData();
        for (String category : entries.keySet())
        {
            LineDataSet dataSet = new LineDataSet(entries.get(category), category);

            int color = 0;

            switch (category)
            {
                case TransactionCategories.Salary:
                    color = R.color.very_light_blue;
                    break;
                case TransactionCategories.Other:
                    color = R.color.white;
                    break;
            }

            dataSet.setColors(color);

            dataSet.setDrawFilled(true);
            dataSet.setFillColor(color);

            dataSet.setCircleColor(color);
            dataSet.setCircleHoleColor(color);

            lineData.addDataSet(dataSet);
        }

        chartIncome.getAxisLeft().setValueFormatter(new YAxisFormatter());
        chartIncome.getAxisLeft().setTextSize(12);

        chartIncome.getXAxis().setValueFormatter(new XAxisTimeFormatter());
        chartIncome.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chartIncome.getXAxis().setGranularityEnabled(true);
        chartIncome.getXAxis().setGranularity(1.0f);
        chartIncome.getXAxis().setLabelCount(5);

        chartIncome.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        chartIncome.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        chartIncome.getDescription().setEnabled(false);
        chartIncome.getAxisRight().setDrawLabels(false);

        chartIncome.setDragEnabled(false);
        chartIncome.setScaleEnabled(false);
        chartIncome.setPinchZoom(false);

        chartIncome.setNoDataTextColor(R.color.black);

        if (entries.size() > 0)
        {
            chartIncome.setData(lineData);
            chartIncome.invalidate();
        }
    }
    private void addExpensesChart(List<TransactionEntity> transactions)
    {
        HashMap<String, Integer> categoryAmounts = new HashMap<>();

        for (int i = transactions.size() - 1; i >= 0; --i)
        {
            TransactionEntity transaction = transactions.get(i);

            String category = transaction.getCategory();
            int amount = transaction.getAmount();

            if (!categoryAmounts.containsKey(category))
                categoryAmounts.put(category, 0);

            categoryAmounts.put(category, categoryAmounts.get(category) + amount);
        }

        List<PieEntry> entries = new ArrayList<>();
        for (String category : categoryAmounts.keySet())
        {
            entries.add(new PieEntry(categoryAmounts.get(category), category));
        }

        int[] colorArray = getResources().getIntArray(R.array.pie_chart_colors);
        ArrayList<Integer> colors = new ArrayList<>(entries.size());
        for (int j : colorArray)
            colors.add(j);

        PieDataSet dataSet = new PieDataSet(entries, "");
        PieData pieData = new PieData(dataSet);

        dataSet.setColors(colors);
        dataSet.setValueTextSize(14);
        dataSet.setValueFormatter(new PieValueFormatter());

        chartExpenses.getDescription().setEnabled(false);
        chartExpenses.getLegend().setEnabled(false);

        chartExpenses.setNoDataTextColor(R.color.black);
        chartExpenses.setHoleColor(R.color.black);

        chartExpenses.setRotationEnabled(true);

        if (entries.size() > 0)
        {
            chartExpenses.setData(pieData);
            chartExpenses.invalidate();
        }
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

    private class XAxisTimeFormatter extends ValueFormatter
    {
        @Override
        public String getAxisLabel(float value, AxisBase axis)
        {
            return DateConverter.format((long)value);
        }
    }
    private class YAxisFormatter extends ValueFormatter
    {
        @Override
        public String getAxisLabel(float value, AxisBase axis)
        {
            return (int)value + "$";
        }
    }
    private class PieValueFormatter extends ValueFormatter
    {
        @Override
        public String getPieLabel(float value, PieEntry pieEntry)
        {
            return (int)value + "$";
        }
    }
}
