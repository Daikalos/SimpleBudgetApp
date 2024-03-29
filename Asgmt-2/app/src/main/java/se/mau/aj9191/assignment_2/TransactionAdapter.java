package se.mau.aj9191.assignment_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends ListAdapter<TransactionEntity, TransactionAdapter.TransactionHolder>
{
    private final Context context;
    private final  TransactionViewModel transactionViewModel;

    public TransactionAdapter(@NonNull DiffUtil.ItemCallback<TransactionEntity> diffCallback, Context context, TransactionViewModel transactionViewModel)
    {
        super(diffCallback);

        this.context = context;
        this.transactionViewModel = transactionViewModel;
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        return TransactionHolder.create(viewGroup, context, transactionViewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position)
    {
        TransactionEntity current = getItem(position);
        holder.bind(current);
    }

    protected static class TransactionDiff extends DiffUtil.ItemCallback<TransactionEntity>
    {
        @Override
        public boolean areItemsTheSame(@NonNull TransactionEntity oldItem, @NonNull TransactionEntity newItem)
        {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TransactionEntity oldItem, @NonNull TransactionEntity newItem)
        {
            return oldItem.equals(newItem);
        }
    }

    protected static class TransactionHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final Context context;
        private final TransactionViewModel transactionViewModel;

        private final ImageView ivCategory;
        private final TextView tvCategory;
        private final TextView tvDate;
        private final TextView tvTitle;
        private final TextView tvAmount;
        private final ImageButton btnRemove;

        private TransactionEntity transaction;

        public TransactionHolder(@NonNull View itemView, Context context, TransactionViewModel transactionViewModel)
        {
            super(itemView);

            this.context = context;
            this.transactionViewModel = transactionViewModel;

            ivCategory = itemView.findViewById(R.id.ivCategory);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }

        public void bind(TransactionEntity transaction)
        {
            this.transaction = transaction;

            ivCategory.setImageDrawable(TransactionCategories.getIconFromType(context, transaction.getCategory()));
            tvCategory.setText(transaction.getCategory());
            tvDate.setText(DateConverter.format(transaction.getDate()));
            tvTitle.setText(transaction.getTitle());
            tvAmount.setText("$" + transaction.getAmount());

            btnRemove.setOnClickListener(this);
        }

        public static TransactionHolder create(ViewGroup viewGroup, Context context, TransactionViewModel transactionViewModel)
        {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.transaction_item, viewGroup, false);

            return new TransactionHolder(view, context, transactionViewModel);
        }

        @Override
        public void onClick(View view)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);

            builder.setTitle(R.string.txt_delete);
            builder.setMessage(R.string.txt_sure);

            builder.setPositiveButton(R.string.txt_yes, (dialogInterface, i) ->
            {
                transactionViewModel.delete(transaction.getId());
                dialogInterface.dismiss();
            });
            builder.setNegativeButton(R.string.txt_no, (dialogInterface, i) -> dialogInterface.dismiss());

            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
