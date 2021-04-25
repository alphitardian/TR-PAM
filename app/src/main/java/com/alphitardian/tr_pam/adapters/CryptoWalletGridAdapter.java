package com.alphitardian.tr_pam.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphitardian.tr_pam.CryptoDetailActivity;
import com.alphitardian.tr_pam.R;
import com.alphitardian.tr_pam.models.AssetsList;
import com.alphitardian.tr_pam.models.AssetsListCoin;
import com.alphitardian.tr_pam.models.CryptoData;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Random;

public class CryptoWalletGridAdapter extends RecyclerView.Adapter<CryptoWalletGridAdapter.ListViewHolder> {

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_ICON = "extra_icon";

    private ArrayList<AssetsListCoin> assetsListsCoin;
    private int[] colors;

    public CryptoWalletGridAdapter(ArrayList<AssetsListCoin> assetsListsCoin) {
        this.assetsListsCoin = assetsListsCoin;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crypto_list_grid, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        AssetsListCoin data = assetsListsCoin.get(position);

        holder.cryptoNameTextView.setText(data.getCoin());
        holder.cryptoValueTextView.setText(data.getTotal() + " Coin");
        holder.cryptoImage.setImageResource(R.drawable.ic_launcher_foreground);
        holder.cryptoCard.setCardBackgroundColor(colors[new Random().nextInt(colors.length)]);
        holder.cryptoImage.setImageResource(getCryptoIcon(data.getCoin()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), CryptoDetailActivity.class);
                intent.putExtra(EXTRA_ID, data.getId()+"");
                intent.putExtra(EXTRA_ICON, getCryptoIcon(data.getCoin()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return assetsListsCoin.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView cryptoNameTextView, cryptoValueTextView;
        private ImageView cryptoImage;
        private MaterialCardView cryptoCard;

        public ListViewHolder(@NonNull View view) {
            super(view);
            cryptoNameTextView = view.findViewById(R.id.crypto_name_textview);
            cryptoValueTextView = view.findViewById(R.id.crypto_value_textview);
            cryptoImage = view.findViewById(R.id.crypto_image);
            cryptoCard = view.findViewById(R.id.crypto_card);

            colors = new int[]{
                    Color.GRAY,
                    Color.MAGENTA,
                    Color.DKGRAY,
                    Color.BLUE,
                    Color.BLACK,
            };
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
