package se.mau.aj9191.assignment_2;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionHolder>
{

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }

    protected static class TransactionHolder extends RecyclerView.ViewHolder
    {

        public TransactionHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }
}
