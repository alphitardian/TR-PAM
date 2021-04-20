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

public class MarketListAdapter extends RecyclerView.Adapter<MarketListAdapter.ListViewHolder> {

    private ArrayList<CryptoData> cryptoData;

    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_PRICE = "extra_price";
    public static final String EXTRA_ICON = "extra_icon";

    public MarketListAdapter(ArrayList<CryptoData> cryptoData) {
        this.cryptoData = cryptoData;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        CryptoData data = cryptoData.get(position);

        holder.updatePercentageTextView.setText(data.getSymbol());
        holder.currentPriceTextView.setText(String.format("%.3f", data.getPrice().getCurrent()));
        holder.cryptoShortNameTextView.setText(data.getSymbol());
        holder.updatePercentageIcon.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
        holder.cryptoImage.setImageResource(R.drawable.ic_launcher_foreground);

        // On Click Event Per Item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), CryptoDetailActivity.class);
                intent.putExtra(EXTRA_NAME, data.getName());
                intent.putExtra(EXTRA_PRICE, String.format("%.3f", data.getPrice().getCurrent()));
                intent.putExtra(EXTRA_ICON, R.drawable.ic_launcher_foreground);
                holder.itemView.getContext().startActivity(intent);

                Toast.makeText(holder.itemView.getContext(), data.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cryptoData.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView updatePercentageTextView, currentPriceTextView, cryptoShortNameTextView;
        private ImageView updatePercentageIcon, cryptoImage;

        public ListViewHolder(@NonNull View view) {
            super(view);
            updatePercentageTextView = view.findViewById(R.id.update_percentage_textview);
            currentPriceTextView = view.findViewById(R.id.current_price_textview);
            cryptoShortNameTextView = view.findViewById(R.id.crypto_shortname_textview);
            updatePercentageIcon = view.findViewById(R.id.update_percentage_icon);
            cryptoImage = view.findViewById(R.id.crypto_image);
        }
    }
}
