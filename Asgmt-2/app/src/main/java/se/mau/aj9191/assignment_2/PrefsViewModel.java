package se.mau.aj9191.assignment_2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;

public class PrefsViewModel extends AndroidViewModel
{
    private final SharedPreferences sharedPreferences;

    public static final String Prefs = "Prefs";
    public static final String FirstName = "FirstName";
    public static final String Surname = "Surname";

    public PrefsViewModel(Application application)
    {
        super(application);
        sharedPreferences = application.getSharedPreferences(Prefs, Context.MODE_PRIVATE);
    }

    public String getFirstName()
    {
        return sharedPreferences.getString(FirstName, "");
    }
    public String getSurname()
    {
        return sharedPreferences.getString(Surname, "");
    }

    public void setFirstName(String firstName)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FirstName, firstName);
        editor.apply();
    }
    public void setSurname(String surname)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Surname, surname);
        editor.apply();
    }
}
