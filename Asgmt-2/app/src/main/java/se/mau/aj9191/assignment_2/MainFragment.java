package se.mau.aj9191.assignment_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

public class MainFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        PrefsViewModel prefsViewModel = new ViewModelProvider(requireActivity()).get(PrefsViewModel.class);

        String firstName = prefsViewModel.getFirstName();
        String surname = prefsViewModel.getSurname();

        if (firstName.isEmpty() || surname.isEmpty())
        {
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fcvMain, new LoginFragment()).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main, container, false);



        return view;
    }
}
