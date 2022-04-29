package se.mau.aj9191.assignment_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends ListAdapter<Transaction, TransactionAdapter.TransactionHolder>
{
    public TransactionAdapter(@NonNull DiffUtil.ItemCallback<Transaction> diffCallback)
    {
        super(diffCallback);
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        return TransactionHolder.create(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position)
    {
        Transaction current = getItem(position);
        holder.bind(current);
    }

    protected static class TransactionDiff extends DiffUtil.ItemCallback<Transaction>
    {
        @Override
        public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem.equals(newItem);
        }
    }

    protected static class TransactionHolder extends RecyclerView.ViewHolder
    {
        private ImageView ivCategory;
        private ImageView ivType;
        private TextView tvCategory;
        private TextView tvDate;
        private TextView tvTitle;
        private TextView tvAmount;

        public TransactionHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        public void bind(Transaction transaction)
        {

        }

        public static TransactionHolder create(ViewGroup viewGroup)
        {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.transaction_item, viewGroup, false);

            return new TransactionHolder(view);
        }
    }
}
