package se.mau.aj9191.assignment_2;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class IncomeCategories
{
    public static final String Salary = "Salary";
    public static final String Other = "Other";

    public static Drawable getIconFromType(Context context, String type)
    {
        switch (type)
        {
            case Salary:
                return ContextCompat.getDrawable(context, R.drawable.ic_salary);
            case Other:
                return ContextCompat.getDrawable(context, R.drawable.ic_other);
        }
        return null;
    }
}
