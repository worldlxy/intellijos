package com.zte.os.oncall24;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.zte.os.oncall24.bean.AbnormalKpi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by worldlxy on 2017/6/9.
 */
public class ShowSingleKpiDataActivity extends Activity implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private LineChart singleCellLineChart;
    private TextView textView;
    private GestureDetector gestureDetector;
    private List abnormalKpiList;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_single_abnormal_kpi);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        String kpidata = intent.getStringExtra("kpidata");
        currentIndex = intent.getIntExtra("currentIndex", 0);
        abnormalKpiList = new Gson().fromJson(kpidata, List.class);

        singleCellLineChart = (LineChart) findViewById(R.id.linechart);
        textView = (TextView) findViewById(R.id.textView2);

        init(currentIndex);

        gestureDetector = new GestureDetector(this, this);
        gestureDetector.setIsLongpressEnabled(true);
        singleCellLineChart.setOnTouchListener(this);
    }

    private void init(int currentIndex) {
        this.currentIndex = (currentIndex + abnormalKpiList.size()) % abnormalKpiList.size();

        AbnormalKpi abnormalKpi = new Gson().fromJson(abnormalKpiList.get(this.currentIndex).toString(), AbnormalKpi.class);

        textView.setText(abnormalKpi.getAbnormalMessage());

        ArrayList<Entry> values = new ArrayList<Entry>();
        ArrayList<Entry> values2 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<>();

        String[] forcastData = abnormalKpi.getForecastData().split(",");
        String[] realData = abnormalKpi.getRealData().split(",");
        for (int i = 0; i < forcastData.length; i++) {
            xVals.add(String.valueOf(i));
            values.add(new Entry(Float.valueOf(forcastData[i]), i, null));
            values2.add(new Entry(Float.valueOf(realData[i]), i, null));
        }

        LineDataSet realDataSet = new LineDataSet(values, "real data");
        realDataSet.setColor(Color.rgb(148, 0, 211));
        realDataSet.setLineWidth(2.0f);
        LineDataSet forcastDataSet = new LineDataSet(values2, "forcast data");
        forcastDataSet.setColor(Color.rgb(0, 0, 0));
        forcastDataSet.setLineWidth(2.0f);

        LineData data = new LineData(xVals);
        data.addDataSet(realDataSet);
        data.addDataSet(forcastDataSet);
        singleCellLineChart.setData(data);

        singleCellLineChart.invalidate();
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        int nextIndex = -1;
        //左滑
        if (motionEvent.getX() - motionEvent1.getX() > 400) {
            nextIndex = (currentIndex + 1 + abnormalKpiList.size()) % abnormalKpiList.size();
        }

        //右滑
        if (motionEvent1.getX() - motionEvent.getX() > 400) {
            nextIndex = (currentIndex - 1 + abnormalKpiList.size()) % abnormalKpiList.size();
        }

        init(nextIndex);
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
}
