package se.mau.aj9191.assignment_2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment
{
    private PrefsViewModel prefsViewModel;

    private TextInputEditText etFirstName;
    private TextInputEditText etSurname;
    private MaterialButton btnLogin;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        prefsViewModel = new ViewModelProvider(requireActivity()).get(PrefsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initializeComponents(view);
        registerListeners();

        return view;
    }

    private void initializeComponents(View view)
    {
        etFirstName = view.findViewById(R.id.etFirstName);
        etSurname = view.findViewById(R.id.etSurname);
        btnLogin = view.findViewById(R.id.btnLogin);
    }

    private void registerListeners()
    {
        btnLogin.setOnClickListener(view ->
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

            prefsViewModel.setFirstName(firstName);
            prefsViewModel.setSurname(surname);

            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fcvMain, new MainFragment()).commit();
        });
    }
}
