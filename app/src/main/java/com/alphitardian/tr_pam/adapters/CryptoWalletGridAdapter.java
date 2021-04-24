package com.alphitardian.tr_pam.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphitardian.tr_pam.R;
import com.alphitardian.tr_pam.models.AssetsList;
import com.alphitardian.tr_pam.models.AssetsListCoin;
import com.alphitardian.tr_pam.models.CryptoData;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Random;

public class CryptoWalletGridAdapter extends RecyclerView.Adapter<CryptoWalletGridAdapter.ListViewHolder> {

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
                    Color.RED,
                    Color.GRAY,
                    Color.MAGENTA,
                    Color.DKGRAY,
                    Color.BLUE,
                    Color.BLACK,
            };
        }
    }
}
