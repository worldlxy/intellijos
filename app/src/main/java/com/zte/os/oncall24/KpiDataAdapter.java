package com.zte.os.oncall24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zte.os.oncall24.bean.AbnormalKpi;

import java.util.List;

/**
 * Created by worldlxy on 2017/6/10.
 */
public class KpiDataAdapter extends BaseAdapter {

    private final Context context;
    private final List abnormalDataList;
    private LayoutInflater mInflater;

    public KpiDataAdapter(Context context, List abnormalDataList) {
        this.context = context;
        this.abnormalDataList = abnormalDataList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return abnormalDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        AbnormalKpi abnormalKpi = new Gson().fromJson(abnormalDataList.get(i).toString(), new TypeToken<AbnormalKpi>(){}.getType());

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.sinlekpiview, null);
            TextView singleKpiTextView = (TextView) convertView.findViewById(R.id.singleKpiText);
            holder.singleKpiTextView = singleKpiTextView;

            singleKpiTextView.setText(abnormalKpi.getAbnormalMessage());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.singleKpiTextView.setText(abnormalKpi.getAbnormalMessage());
        }

        return convertView;
    }

    private class ViewHolder {
        public TextView singleKpiTextView;
    }
}
