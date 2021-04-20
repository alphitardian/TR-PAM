package com.alphitardian.tr_pam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphitardian.tr_pam.adapter.MarketListAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class CryptoDetailActivity extends AppCompatActivity {

    TextView cryptoNameTextView, currentPriceTextView;
    ImageView cryptoImage;
    LineChart chart;

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
        chart.getXAxis().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.animateXY(1000, 1000);

        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(1, 50));
        values.add(new Entry(2, 100));
        values.add(new Entry(3, 70));
        values.add(new Entry(4, 30));
        values.add(new Entry(5, 120));
        values.add(new Entry(6, 110));
        values.add(new Entry(7, 20));

        LineDataSet set1;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
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
}