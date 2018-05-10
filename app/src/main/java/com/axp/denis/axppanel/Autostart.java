package com.axp.denis.axppanel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Background service to keep an eye on Dynamic background preferences.
 */

public class Autostart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Autostart backgroundScheduler...", Toast.LENGTH_SHORT).show();
        context.startService(new Intent (context, BackgroundScheduleService.class));
    }
}
