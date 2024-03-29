package se.mau.aj9191.assignment_2;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class TransactionType
{
    public static final String Expenses = "Expenses";
    public static final String Income = "Income";

    public static final String[] Type = { Expenses, Income };

    public static Drawable getIconFromType(Context context, String type)
    {
        switch (type)
        {
            case Expenses:
                return ContextCompat.getDrawable(context, R.drawable.ic_payments);
            case Income:
                return ContextCompat.getDrawable(context, R.drawable.ic_money);
        }
        return null;
    }
}
