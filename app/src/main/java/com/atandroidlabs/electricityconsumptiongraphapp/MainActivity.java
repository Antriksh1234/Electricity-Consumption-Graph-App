package com.atandroidlabs.electricityconsumptiongraphapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

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
        //lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawVerticalHighlightIndicator(false);
    }

    private void setLineChart(final LineChart chart) {

        //setting a NO background for the graph
        chart.setDrawGridBackground(false);

        final ElectricalMarkerView marker = new ElectricalMarkerView(this, R.layout.electrical_graph_markerview);
        marker.setChartView(chart);
        //set the marker for the lineChart
        chart.setMarker(marker);

        chart.setDrawBorders(false);
        //Removing the legend(small square at the bottom left) of the consumption graph
        chart.getLegend().setEnabled(false);

        ////////////////////X Axis //////////////////////////////////////////////
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setAxisMinimum(0f);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setAxisLineWidth(0.7f);
        //Setting textColor of labels of X axis and their rotation by -45 degrees
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setLabelCount(consumptions.size());
        chart.getXAxis().setLabelRotationAngle(-45);

        //Formatting the text of the X Axis for specifying the switch number
        formatXAxisIndexLabels(chart.getXAxis());
        //////////////////X Axis ///////////////////////////////////

        //////////////////////Y Axis /////////////////////////
        //Setting position only as per first quadrant
        chart.getAxisLeft().setDrawGridLines(true);

        int grayGridLineColor = ContextCompat.getColor(this,R.color.gridLineColor);
        chart.getAxisLeft().setGridColor(grayGridLineColor);
        chart.getAxisLeft().setGridLineWidth(0.7f);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisLeft().setDrawAxisLine(false);

        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);

        //Setting textColor of labels, their count and their size for Y Axis
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getAxisLeft().setTextSize(10f);
        chart.getAxisLeft().setLabelCount(consumptions.size()); //is dynamic, depends on size of arrayList

        //Add a space in YAxis labels to make the labels more further away from the Y Axis
        formatYAxisIndexLabels(chart.getAxisLeft());
        /////////////////////Y Axis /////////////////////////
    }

    private void setChartDescription(LineChart chart) {
        Description description = new Description();
        //description.setText("Switches vs Electrical units used graph");
        description.setText("");
        chart.setDescription(description);
//        chart.getDescription().setTextColor(Color.WHITE);
//        chart.getDescription().setTextSize(10f);
    }

    private void formatXAxisIndexLabels(final XAxis xAxis) {
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value == xAxis.getAxisMinimum()) {
                    return "";
                }
                return "Switch " + (int)value;
            }
        });
    }

    private void formatYAxisIndexLabels(final YAxis yAxis) {
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int)value + "  ";
            }
        });
    }

    private ArrayList<Entry> getLineEntries(ArrayList<Consumption> consumptions) {
        lineEntries = new ArrayList<>();
        for (Consumption consumption : consumptions) {
            lineEntries.add(new Entry(consumption.switchNumber, consumption.electricalUnits));
        }
        return lineEntries;
    }

    private void getConsumptionData() {
        Consumption c1 = new Consumption(1, 10);
        Consumption c2 = new Consumption(2, 15);
        Consumption c3 = new Consumption(3, 25);
        Consumption c4 = new Consumption(4, 17);
        Consumption c5 = new Consumption(5, 30);
        Consumption c6 = new Consumption(6, 40);

        consumptions.add(c1);
        consumptions.add(c2);
        consumptions.add(c3);
        consumptions.add(c4);
        consumptions.add(c5);
        consumptions.add(c6);
    }
}