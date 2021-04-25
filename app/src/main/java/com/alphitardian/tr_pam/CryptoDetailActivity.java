package com.alphitardian.tr_pam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alphitardian.tr_pam.adapters.MarketListAdapter;
import com.alphitardian.tr_pam.apis.ApiList;
import com.alphitardian.tr_pam.apis.RetrofitClient;
import com.alphitardian.tr_pam.models.AssetsInSingle;
import com.alphitardian.tr_pam.models.AssetsInSingleData;
import com.alphitardian.tr_pam.models.CryptoPrice;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryptoDetailActivity extends AppCompatActivity {

    private TextView cryptoNameTextView, currentPriceTextView, totalCoin, totalBuyPrice, avgBuyPrice, totalProfit;
    private ImageView cryptoImage, webViewButton;
    private Button sellButton;
    private LineChart chart;

    private final int BUY_CRYPTO = 33;
    private final int SELL_CRYPTO = 34;

    SharedPreferences pref;

    private CryptoPrice priceHistory;

    NumberFormat format = NumberFormat.getCurrencyInstance();

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_PRICE = "extra_price";
    public static final String EXTRA_SYMBOL = "extra_symbol";
    public static final String EXTRA_ICON = "extra_icon";
    public static final String EXTRA_HISTORY = "extra_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_detail);

        cryptoNameTextView = findViewById(R.id.crypto_name_textview);
        currentPriceTextView = findViewById(R.id.current_price_textview);
        totalCoin = findViewById(R.id.totalCoin);
        totalBuyPrice = findViewById(R.id.totalBuyPrice);
        avgBuyPrice = findViewById(R.id.avgBuyPrice);
        totalProfit = findViewById(R.id.totalProfit);
        cryptoImage = findViewById(R.id.crypto_image);
        sellButton = findViewById(R.id.sell_button);
        chart = findViewById(R.id.line_chart);
        webViewButton = findViewById(R.id.webViewButton);
        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        cryptoNameTextView.setText(getIntent().getStringExtra(MarketListAdapter.EXTRA_NAME));
        currentPriceTextView.setText(getIntent().getStringExtra(MarketListAdapter.EXTRA_PRICE));
        cryptoImage.setImageResource(getIntent().getIntExtra(MarketListAdapter.EXTRA_ICON, 0));

        webViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CryptoDetailActivity.this, WebViewActivity.class);
                intent.putExtra(EXTRA_SYMBOL, getIntent().getStringExtra(MarketListAdapter.EXTRA_SYMBOL));
                startActivity(intent);
            }
        });

        getAsset();
        createGraph();
    }

    private void createGraph() {

        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("90d");
        xAxisLabel.add("60d");
        xAxisLabel.add("30d");
        xAxisLabel.add("7d");
        xAxisLabel.add("24h");
        xAxisLabel.add("1h");

        chart.setTouchEnabled(true);
        chart.setPinchZoom(true);
        chart.setDrawGridBackground(false);
        chart.setGridBackgroundColor(Color.WHITE);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawBorders(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawLabels(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.animateXY(1000, 1000);

        priceHistory = getIntent().getParcelableExtra(MarketListAdapter.EXTRA_HISTORY);

        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(1, (float) priceHistory.getIn90d()));
        values.add(new Entry(2, (float) priceHistory.getIn60d()));
        values.add(new Entry(3, (float) priceHistory.getIn30d()));
        values.add(new Entry(4, (float) priceHistory.getIn7d()));
        values.add(new Entry(5, (float) priceHistory.getIn24h()));
        values.add(new Entry(6, (float) priceHistory.getIn1h()));

        LineDataSet set1;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

            XAxis xAxis = chart.getXAxis();
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return xAxisLabel.get((int) value);
                }
            });
        } else {
            set1 = new LineDataSet(values, "");
            set1.setDrawIcons(false);
            set1.setColor(Color.WHITE);
            set1.setDrawCircleHole(false);
            set1.setDrawFilled(true);
            set1.setDrawValues(false);
            set1.setDrawCircles(false);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            chart.setData(data);
        }
    }

    public void buyCrypto(View v) {

        Intent intent = new Intent(this, BuyCryptoActivity.class);
        intent.putExtra(EXTRA_NAME, getIntent().getStringExtra(MarketListAdapter.EXTRA_NAME));
        intent.putExtra(EXTRA_PRICE, getIntent().getStringExtra(MarketListAdapter.EXTRA_PRICE));
        intent.putExtra(EXTRA_ID, getIntent().getStringExtra(MarketListAdapter.EXTRA_ID));

        startActivityForResult(intent, BUY_CRYPTO);
    }

    public void sellCrypto(View v) {

        Intent intent = new Intent(this, SellCrypto.class);
        intent.putExtra(EXTRA_NAME, getIntent().getStringExtra(MarketListAdapter.EXTRA_NAME));
        intent.putExtra(EXTRA_PRICE, getIntent().getStringExtra(MarketListAdapter.EXTRA_PRICE));
        intent.putExtra(EXTRA_ID, getIntent().getStringExtra(MarketListAdapter.EXTRA_ID));

        startActivityForResult(intent, SELL_CRYPTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BUY_CRYPTO) {
            getAsset();
        }

        if (requestCode == SELL_CRYPTO) {
            getAsset();
        }
    }

    public void getAsset() {
        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<AssetsInSingle> call = apiList.getSingleCryptoReport(pref.getString("userId", ""), Integer.parseInt(getIntent().getStringExtra(MarketListAdapter.EXTRA_ID)));

        call.enqueue(new Callback<AssetsInSingle>() {
            @Override
            public void onResponse(Call<AssetsInSingle> call, Response<AssetsInSingle> response) {
                if (response.isSuccessful()) {

                    format.setMaximumFractionDigits(0);

                    format.setCurrency(Currency.getInstance("USD"));

                    AssetsInSingle assetsInSingle = response.body();
                    AssetsInSingleData data = assetsInSingle.getData();

                    Double currentPrice = Double.parseDouble(getIntent().getStringExtra(MarketListAdapter.EXTRA_PRICE));

                    Double avgBuy = 0.0;

                    if (data.getAvgBuy() != null) {
                        avgBuy = data.getAvgBuy();
                    }

                    totalCoin.setText(data.getAmount() + " Coin");
                    totalBuyPrice.setText(format.format(data.getTotalAsset()));
                    avgBuyPrice.setText(format.format(avgBuy));
                    if (data.getAmount() > 0) {
                        totalProfit.setText(format.format(avgBuy - currentPrice));
                    } else {
                        totalProfit.setText(format.format(0.0));
                    }

                    if (data.getAmount() < 1) {
                        sellButton.setEnabled(false);
                    } else {
                        sellButton.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<AssetsInSingle> call, Throwable t) {

            }
        });
    }


}
