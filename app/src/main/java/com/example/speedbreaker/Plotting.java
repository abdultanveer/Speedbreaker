package com.example.speedbreaker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class Plotting extends Activity {
    LineChart mpLineChart;
    LineChart mpLineChart1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mpLineChart = findViewById(R.id.chart);
        mpLineChart1 = findViewById(R.id.chart1);
        LineDataSet lineDataSet = new LineDataSet(dataValue(),"the longitude and latitude");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setCircleColor(Color.YELLOW);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleHoleRadius(3);


        LineDataSet lineDataSet1 = new LineDataSet(dataValue1(),"the speed");
        ArrayList<ILineDataSet> dataSet = new ArrayList<>();
        dataSet.add(lineDataSet1);
        lineDataSet1.setColor(Color.BLUE);
        lineDataSet1.setCircleColor(Color.GREEN);
        lineDataSet1.setHighlightEnabled(true);
        lineDataSet1.setLineWidth(2);
        lineDataSet1.setCircleRadius(6);
        lineDataSet1.setCircleHoleRadius(3);



        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();

        LineData data1 = new LineData(dataSet);
        mpLineChart1.setData(data1);
        mpLineChart1.invalidate();
    }

    private ArrayList<Entry> dataValue() {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 12.800359f,2345));
        dataVals.add(new Entry(1, 35.587730f,5677));
        dataVals.add(new Entry(2, 60.91657f,6789));
        return dataVals;
    }

    private ArrayList<Entry> dataValue1() {
        ArrayList<Entry> dataVals1 = new ArrayList<Entry>();
        dataVals1.add(new Entry(0, 28.456f,2345));
        dataVals1.add(new Entry(1, 5.3456f,5677));
        dataVals1.add(new Entry(2, 90.91657f,6789));
        return dataVals1;
    }

}