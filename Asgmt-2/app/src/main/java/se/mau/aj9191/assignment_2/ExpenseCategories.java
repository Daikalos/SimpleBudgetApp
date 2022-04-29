package se.mau.aj9191.assignment_2;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class ExpenseCategories
{
    public static final String Food = "Food";
    public static final String Leisure = "Leisure";
    public static final String Travel = "Travel";
    public static final String Accommodation = "Accommodation";
    public static final String Other = "Other";

    public static Drawable getIconFromType(Context context, String type)
    {
        switch (type)
        {
            case Food:
                return ContextCompat.getDrawable(context, R.drawable.ic_salary);
            case Leisure:
                return ContextCompat.getDrawable(context, R.drawable.ic_leisure);
            case Travel:
                return ContextCompat.getDrawable(context, R.drawable.ic_travel);
            case Accommodation:
                return ContextCompat.getDrawable(context, R.drawable.ic_accommodation);
            case Other:
                return ContextCompat.getDrawable(context, R.drawable.ic_other);
        }
        return null;
    }
}
