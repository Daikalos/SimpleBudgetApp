package se.mau.aj9191.assignment_2;

import android.content.Context;
import android.widget.Toast;

public class ShowToast
{
    private static Toast toast;

    public static void show(Context context, String text)
    {
        if (toast != null)
            toast.cancel();

        (toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)).show();
    }
}
