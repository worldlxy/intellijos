package com.zte.os.oncall24;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

public class ShowAbNormalKpiActivity extends ListActivity {
    List abnormalDataList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_abnormal_kpi);

        Intent intent = getIntent();
        if(intent != null) {
            String abnormalData = intent.getStringExtra("abnormalData");
            if(abnormalData != null) {
                abnormalDataList = new Gson().fromJson(abnormalData, List.class);
                setListAdapter(new KpiDataAdapter(this, abnormalDataList));
            }
        }

        ListView listview = (ListView) findViewById(android.R.id.list);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShowAbNormalKpiActivity.this, ShowSingleKpiDataActivity.class);
                if(abnormalDataList != null) {
                    intent.putExtra("kpidata",new Gson().toJson(abnormalDataList));
                    intent.putExtra("currentIndex", i);
                }
                startActivity(intent);
            }
        });
    }
}
