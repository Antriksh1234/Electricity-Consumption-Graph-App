package com.atandroidlabs.electricityconsumptiongraphapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Consumption> consumptions;
    ArrayList<Entry> lineEntries;
    LineDataSet lineDataSet;
    LineData lineData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LineChart chart;
        chart = findViewById(R.id.lineChart);
        chart.setDoubleTapToZoomEnabled(false);
        consumptions = new ArrayList<>();
        //Get the consumption data from somewhere
        getConsumptionData();

        //After getting the consumption data manipulate it accordingly into an ArrayList of entries for mp android chart library
        lineEntries = getLineEntries(consumptions);

        lineDataSet = new LineDataSet(lineEntries, "");
        lineData = new LineData(lineDataSet);
        chart.setData(lineData);

        setLineDataSet();
        setLineChart(chart);
        setChartDescription(chart);

//        chart.invalidate();
    }

    private void setLineDataSet() {
        int lineColor = ContextCompat.getColor(this, R.color.lineColor);
        int pointColor = ContextCompat.getColor(this, R.color.pointColor);
        lineDataSet.setCircleColor(pointColor);
        lineDataSet.setCircleRadius(7f);
        lineDataSet.setLineWidth(6f);
        lineDataSet.setColor(lineColor);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
    }

    private void setLineChart(LineChart chart) {
        //setting a NO background for the graph
        chart.setDrawGridBackground(false);

        //Setting position only as per first quadrant
        chart.getAxisLeft().setDrawGridLines(true);
        chart.getAxisLeft().setGridLineWidth(1f);

        chart.getAxisRight().setDrawGridLines(false);

        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setDrawBorders(false);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getXAxis().setAxisMinimum(0f);

        chart.getXAxis().setAxisLineWidth(3f);
        chart.getAxisLeft().setAxisLineWidth(3f);

        //Setting textColor of labels
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getAxisLeft().setTextSize(10f);

        chart.getXAxis().setLabelCount(consumptions.size());
        chart.getAxisLeft().setLabelCount(consumptions.size());

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                float x = e.getX();
                float y = e.getY();

                Toast.makeText(MainActivity.this, "Switch- " + x + ", Electrical unit- " + y, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {
                //Nothing done
            }
        });
    }

    private void setChartDescription(LineChart chart) {
        Description description = new Description();
        //description.setText("Switches vs Electrical units used graph");
        description.setText("");
        chart.setDescription(description);
//        chart.getDescription().setTextColor(Color.WHITE);
//        chart.getDescription().setTextSize(10f);
    }

    private ArrayList<Entry> getLineEntries(ArrayList<Consumption> consumptions) {
        lineEntries = new ArrayList<>();
        for (Consumption consumption : consumptions) {
            lineEntries.add(new Entry(consumption.noOfSwitches, consumption.electricalUnits));
        }
        return lineEntries;
    }

    private void getConsumptionData() {
        Consumption c1 = new Consumption(10, 20);
        Consumption c2 = new Consumption(20, 45);
        Consumption c3 = new Consumption(30, 50);
        Consumption c4 = new Consumption(40, 60);
        Consumption c5 = new Consumption(50, 65);

        consumptions.add(c1);
        consumptions.add(c2);
        consumptions.add(c3);
        consumptions.add(c4);
        consumptions.add(c5);
    }
}