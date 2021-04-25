package com.alphitardian.tr_pam.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
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
import com.alphitardian.tr_pam.models.CryptoData;
import com.alphitardian.tr_pam.models.CryptoPrice;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

public class MarketListAdapter extends RecyclerView.Adapter<MarketListAdapter.ListViewHolder> {

    private ArrayList<CryptoData> cryptoData;

    NumberFormat format = NumberFormat.getCurrencyInstance();

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_PRICE = "extra_price";
    public static final String EXTRA_ICON = "extra_icon";
    public static final String EXTRA_HISTORY = "extra_history";
    public static final String EXTRA_SYMBOL = "extra_symbol";

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
        format.setMaximumFractionDigits(3);
        format.setCurrency(Currency.getInstance("USD"));

        CryptoData data = cryptoData.get(position);

        Double currentMovement = (data.getPrice().getCurrent() - data.getPrice().getIn24h()) * 100/data.getPrice().getIn24h();
        holder.updatePercentageTextView.setText(String.format("%.3f", currentMovement) + "%");

        if(currentMovement < 0){
            holder.updatePercentageTextView.setTextColor(Color.parseColor("#D32F2F"));
            holder.updatePercentageIcon.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
        }else{
            holder.updatePercentageTextView.setTextColor(Color.parseColor("#388E3C"));
            holder.updatePercentageIcon.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
        }

        holder.currentPriceTextView.setText(format.format(data.getPrice().getCurrent()));
        holder.cryptoShortNameTextView.setText(data.getSymbol());
        holder.cryptoImage.setImageResource(getCryptoIcon(data.getSymbol()));

        // On Click Event Per Item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), CryptoDetailActivity.class);
                intent.putExtra(EXTRA_ID, data.getId());
                intent.putExtra(EXTRA_NAME, data.getName());
                intent.putExtra(EXTRA_SYMBOL, data.getSymbol());
                intent.putExtra(EXTRA_PRICE, data.getPrice().getCurrent());
                intent.putExtra(EXTRA_ICON, getCryptoIcon(data.getSymbol()));

                CryptoPrice cryptoPrice = new CryptoPrice(
                        data.getPrice().getCurrent(),
                        data.getPrice().getIn1h(),
                        data.getPrice().getIn24h(),
                        data.getPrice().getIn7d(),
                        data.getPrice().getIn30d(),
                        data.getPrice().getIn60d(),
                        data.getPrice().getIn90d()
                );

                intent.putExtra(EXTRA_HISTORY, cryptoPrice);
                holder.itemView.getContext().startActivity(intent);
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

    public static int getCryptoIcon(String name) {
        switch (name) {
            case "BTC":
                return R.drawable.btc_icon;
            case "Bitcoin":
                return R.drawable.btc_icon;
            case "ETH":
                return R.drawable.eth_icon;
            case "Ethereum":
                return R.drawable.eth_icon;
            case "BNB":
                return R.drawable.bnb_icon;
            case "Binance Coin":
                return R.drawable.bnb_icon;
            case "XRP":
                return R.drawable.xrp_icon;
            case "USDT":
                return R.drawable.usdt_icon;
            case "Tether":
                return R.drawable.usdt_icon;
            case "ADA":
                return R.drawable.ada_icon;
            case "Cardano":
                return R.drawable.ada_icon;
            case "DOGE":
                return R.drawable.doge_icon;
            case "Dogecoin":
                return R.drawable.doge_icon;
            case "DOT":
                return R.drawable.dot_icon;
            case "Polkadot":
                return R.drawable.dot_icon;
            case "UNI":
                return R.drawable.uni_icon;
            case "Uniswap":
                return R.drawable.uni_icon;
            case "LTC":
                return R.drawable.ltc_icon;
            case "Litecoin":
                return R.drawable.ltc_icon;
            default:
                return R.drawable.btc_icon;
        }
    }

}
