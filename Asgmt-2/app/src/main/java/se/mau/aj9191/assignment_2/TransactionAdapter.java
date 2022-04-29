package se.mau.aj9191.assignment_2;

import android.content.Context;
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
    private final Context context;

    public TransactionAdapter(Context context, @NonNull DiffUtil.ItemCallback<Transaction> diffCallback)
    {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        return TransactionHolder.create(context, viewGroup);
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
        public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem)
        {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem)
        {
            return oldItem.equals(newItem);
        }
    }

    protected static class TransactionHolder extends RecyclerView.ViewHolder
    {
        private final Context context;

        private final ImageView ivCategory;
        private final TextView tvCategory;
        private final TextView tvDate;
        private final TextView tvTitle;
        private final TextView tvAmount;

        public TransactionHolder(Context context, @NonNull View itemView)
        {
            super(itemView);

            this.context = context;

            ivCategory = itemView.findViewById(R.id.ivCategory);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }

        public void bind(Transaction transaction)
        {
            ivCategory.setImageDrawable(TransactionCategories.getIconFromType(context, transaction.getCategory()));
            tvCategory.setText(transaction.getCategory());
            tvDate.setText(DateConverter.toTimestamp(transaction.getDate()));
            tvTitle.setText(transaction.getTitle());
            tvAmount.setText("$" + transaction.getAmount());
        }

        public static TransactionHolder create(Context context, ViewGroup viewGroup)
        {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.transaction_item, viewGroup, false);

            return new TransactionHolder(context, view);
        }
    }
}
