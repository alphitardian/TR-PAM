package com.alphitardian.tr_pam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphitardian.tr_pam.adapters.MarketListAdapter;
import com.alphitardian.tr_pam.models.CryptoPrice;
import com.alphitardian.tr_pam.utils.LabelFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class CryptoDetailActivity extends AppCompatActivity {

    private TextView cryptoNameTextView, currentPriceTextView;
    private ImageView cryptoImage;
    private LineChart chart;

    private CryptoPrice priceHistory;

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_PRICE = "extra_price";
    public static final String EXTRA_ICON = "extra_icon";
    public static final String EXTRA_HISTORY = "extra_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_detail);

        cryptoNameTextView = findViewById(R.id.crypto_name_textview);
        currentPriceTextView = findViewById(R.id.current_price_textview);
        cryptoImage = findViewById(R.id.crypto_image);
        chart = findViewById(R.id.line_chart);

        cryptoNameTextView.setText(getIntent().getStringExtra(MarketListAdapter.EXTRA_NAME));
        currentPriceTextView.setText(getIntent().getStringExtra(MarketListAdapter.EXTRA_PRICE));
        cryptoImage.setImageResource(getIntent().getIntExtra(MarketListAdapter.EXTRA_ICON, 0));

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

        startActivity(intent);
    }


}
