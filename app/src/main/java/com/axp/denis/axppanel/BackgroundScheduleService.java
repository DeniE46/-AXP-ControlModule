package com.axp.denis.axppanel;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Special service to switch styles of Dynamic background.
 */

public class BackgroundScheduleService extends Service {

    IntentFilter intentFilter;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Background scheduler started", Toast.LENGTH_SHORT).show();
            intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_TIME_TICK);
            intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            intentFilter.addAction(Intent.ACTION_TIME_CHANGED);

            registerReceiver(timeChangedReceiver, intentFilter);

    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(timeChangedReceiver);
    }

    private final BroadcastReceiver timeChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                Date date = new Date() ;
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                dateFormat.format(date);
                System.out.println(dateFormat.format(date));
                try {
                    if ((dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("08:00")))&&((dateFormat.parse(dateFormat.format(date)).before(dateFormat.parse("20:00"))))) {
                        updateBackground(0);
                    } else {
                        updateBackground(1);
                    }
                }
                catch (ParseException e){

                }

        }
    };

    void updateBackground(int configuration){
        Intent intent = new Intent();
        intent.setAction("CHANGE_WALL");
        intent.putExtra("Value", configuration);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
