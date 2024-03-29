package se.mau.aj9191.assignment_2;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class TransactionCategories
{
    public static final String Food = "Food";
    public static final String Leisure = "Leisure";
    public static final String Travel = "Travel";
    public static final String Accommodation = "Accommodation";
    public static final String Salary = "Salary";
    public static final String Other = "Other";

    public static String[] Expenses = { Food, Leisure, Travel, Accommodation, Other };
    public static String[] Income = { Salary, Other };

    public static Drawable getIconFromType(Context context, String type)
    {
        switch (type)
        {
            case Food:
                return ContextCompat.getDrawable(context, R.drawable.ic_food);
            case Leisure:
                return ContextCompat.getDrawable(context, R.drawable.ic_leisure);
            case Travel:
                return ContextCompat.getDrawable(context, R.drawable.ic_travel);
            case Accommodation:
                return ContextCompat.getDrawable(context, R.drawable.ic_accommodation);
            case Salary:
                return ContextCompat.getDrawable(context, R.drawable.ic_salary);
            case Other:
                return ContextCompat.getDrawable(context, R.drawable.ic_other);
        }
        return null;
    }

    public static String[] getCategories(@NonNull String type)
    {
        switch (type)
        {
            case TransactionType.Expenses:
                return Expenses;
            case TransactionType.Income:
                return Income;
        }
        return new String[0];
    }
}
