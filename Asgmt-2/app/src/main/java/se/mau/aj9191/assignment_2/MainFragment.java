package se.mau.aj9191.assignment_2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener
{
    private BottomNavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance)
    {
        super.onViewCreated(view, savedInstance);

        initializeComponents(view);
        registerListeners();
    }

    private void initializeComponents(View view)
    {
        navigationView = view.findViewById(R.id.bottom_navigation);
    }

    private void registerListeners()
    {
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fcvCurrent);
        String tag = currentFragment.getTag();

        switch (item.getItemId())
        {
            case R.id.btnHome:
                if (tag != null && !tag.equals("home"))
                {
                    fragmentTransaction.replace(R.id.fcvCurrent, new OverviewFragment(), "home");
                }
                break;
            case R.id.btnIncome:
                if (tag == null || !tag.equals("income"))
                {
                    fragmentTransaction.replace(R.id.fcvCurrent, new TransactionFragment(TransactionType.Income), "income");
                    fragmentTransaction.addToBackStack("income");
                }
                break;
            case R.id.btnExpenditure:
                if (tag == null || !tag.equals("expenditure"))
                {
                    fragmentTransaction.replace(R.id.fcvCurrent, new TransactionFragment(TransactionType.Expenditure), "expenditure");
                    fragmentTransaction.addToBackStack("expenditure");
                }
                break;
        }

        fragmentTransaction.commit();

        return true;
    }
}
