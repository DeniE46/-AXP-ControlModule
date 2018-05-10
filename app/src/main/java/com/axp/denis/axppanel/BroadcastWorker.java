package com.axp.denis.axppanel;

import android.content.Context;
import android.content.Intent;

/**
 * Util class to send various BroadcastIntents to the system.
 */

public class BroadcastWorker {
    Context context;

    public BroadcastWorker(Context context) {
        this.context = context;
    }

    void setButtonsVisibility(int state){
        Intent intent = new Intent();
        intent.setAction("SET_BUTTONS_VISIBILITY");
        intent.putExtra("State", state);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(intent);
    }

    void hideProgressView(){
        Intent intent = new Intent();
        intent.setAction("HIDE_PROGRESS_VIEW");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(intent);
    }

    void hidePercentage(){
        Intent intent = new Intent();
        intent.setAction("HIDE_PERCENTAGE_VIEW");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(intent);
    }

    void changeBackground(int configuration){
        Intent intent = new Intent();
        intent.setAction("CHANGE_WALL");
        intent.putExtra("Value", configuration);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(intent);
    }

    void filterLaunchers(boolean filterIsActivated){
        Intent intent = new Intent();
        intent.setAction("FILTER_LAUNCHER");
        intent.putExtra("Value", filterIsActivated);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(intent);
    }

    void launchThemeViewer(Context context){
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.denie.xperiathemeviewer");
        context.startActivity(intent);
    }


}
