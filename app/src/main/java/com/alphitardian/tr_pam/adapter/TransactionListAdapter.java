package com.alphitardian.tr_pam.adapter;

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
import com.alphitardian.tr_pam.model.CryptoData;

import java.util.ArrayList;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ListViewHolder> {

    private ArrayList<CryptoData> cryptoData;

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
        holder.cryptoImage.setImageResource(R.drawable.ic_launcher_foreground);

        // On Click Event Per Item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), data.getName(), Toast.LENGTH_SHORT).show();
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
