package com.zte.os.oncall24.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.zte.os.oncall24.R;
import com.zte.os.oncall24.ShowAbNormalKpiActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by worldlxy on 2017/6/8.
 */
public class QueryAbnormalKpiService extends Service implements Runnable {

    private String userName;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.userName = intent.getExtras().getString("username");
        new Thread(this).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void run() {
        while (true) {
            URL url;
            try {
                url = new URL("http://intellijos.applinzi.com/fetchabnormalkpi?username=" + userName);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Charset", "UTF-8");
                connection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");

                if (connection.getResponseCode() == 200) {
                    InputStream inputStream = connection.getInputStream();
                    byte[] readData = readStream(inputStream);
                    String json = new String(readData);
                    List abnormalData = new Gson().fromJson(json, List.class);
                    sendNotification(abnormalData);
                } else {
                    Log.i(QueryAbnormalKpiService.class.getName(), "can not get abnormal message");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            snap(10000);
        }
    }

    private void sendNotification(List abnormalData) {
        if (abnormalData.isEmpty()) {
            return;
        }

        Intent notificationIntent = new Intent(this, ShowAbNormalKpiActivity.class);
        notificationIntent.putExtra("abnormalData", new Gson().toJson(abnormalData));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this).setTicker("Receive normal cell kpi")
                .setSmallIcon(R.drawable.intllijos).setContentIntent(pendingIntent)
                .setContentTitle("Normal Cell KPI").
                        setContentText("There is(are) " + abnormalData.size() + " cell have abnormal kpi data.").
                setAutoCancel(true).setOngoing(false).build();
        startForeground(1, notification);

    }

    private byte[] readStream(InputStream inputStream) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                bout.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(bout);
            close(inputStream);
        }
        return bout.toByteArray();
    }

    private void close(AutoCloseable autoCloseable) {
        try {
            if (autoCloseable != null) {
                autoCloseable.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void snap(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
