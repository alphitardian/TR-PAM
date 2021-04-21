package com.alphitardian.tr_pam.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphitardian.tr_pam.CryptoDetailActivity;
import com.alphitardian.tr_pam.R;
import com.alphitardian.tr_pam.TransactionDetailActivity;
import com.alphitardian.tr_pam.models.CryptoData;

import java.util.ArrayList;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ListViewHolder> {

    private ArrayList<CryptoData> cryptoData;

    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_PRICE = "extra_price";
    public static final String EXTRA_ICON = "extra_icon";
    public static final String EXTRA_STATUS = "extra_status";
    public static final String EXTRA_DATE = "extra_date";

    public TransactionListAdapter(ArrayList<CryptoData> cryptoData) {
        this.cryptoData = cryptoData;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        CryptoData data = cryptoData.get(position);

        holder.cryptoNameTextView.setText(data.getName());
        holder.priceTextView.setText(String.format("%.3f", data.getPrice().getCurrent()));
        holder.transferConfirmationTextView.setText(data.getName());
        holder.dateTextView.setText(data.getLastUpdate());
        holder.cryptoImage.setImageResource(MarketListAdapter.getCryptoIcon(data.getSymbol()));

        // On Click Event Per Item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), TransactionDetailActivity.class);
                intent.putExtra(EXTRA_NAME, data.getName());
                intent.putExtra(EXTRA_PRICE, String.format("%.3f", data.getPrice().getCurrent()));
                intent.putExtra(EXTRA_ICON, MarketListAdapter.getCryptoIcon(data.getSymbol()));
                intent.putExtra(EXTRA_STATUS, "Transfer Successful");
                intent.putExtra(EXTRA_DATE, data.getLastUpdate());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cryptoData.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView cryptoNameTextView, transferConfirmationTextView, dateTextView, priceTextView;
        private ImageView cryptoImage;

        public ListViewHolder(@NonNull View view) {
            super(view);
            cryptoNameTextView = view.findViewById(R.id.crypto_name_textview);
            transferConfirmationTextView = view.findViewById(R.id.transfer_confirmation_textview);
            dateTextView = view.findViewById(R.id.date_textview);
            priceTextView = view.findViewById(R.id.price_textview);
            cryptoImage = view.findViewById(R.id.crypto_image);
        }
    }
}
