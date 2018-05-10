package com.axp.denis.axppanel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.axp.denis.axppanel.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding activityMainBinding;
    Intent axpService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        axpService = new Intent(MainActivity.this, BackgroundScheduleService.class);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.buttonStart.setOnClickListener(this);
        activityMainBinding.hideProgress.setOnClickListener(this);
        activityMainBinding.buttonsVisibility.setOnClickListener(this);
        activityMainBinding.swapNotes.setOnClickListener(this);
        activityMainBinding.swapSketches.setOnClickListener(this);

        activityMainBinding.buttonsVisibility.setChecked(true);
        activityMainBinding.swapNotes.setChecked(false);
        activityMainBinding.swapSketches.setChecked(false);
        activityMainBinding.swapNotes.setEnabled(true);
        activityMainBinding.swapSketches.setEnabled(true);
        activityMainBinding.enableWall.setChecked(false);
        if(!activityMainBinding.enableWall.isChecked()){
            MainActivity.this.startService(axpService);
        }
        activityMainBinding.changeWall.setEnabled(false);
        activityMainBinding.changeWall.setChecked(false);
        MainActivity.this.startService(axpService);

        //set buttons visibility
        activityMainBinding.buttonsVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityMainBinding.buttonsVisibility.isChecked()){
                    setButtonsVisibility(1);
                    activityMainBinding.swapNotes.setEnabled(true);
                    activityMainBinding.swapSketches.setEnabled(true);
                }
                else{
                    setButtonsVisibility(0);
                    activityMainBinding.swapNotes.setEnabled(false);
                    activityMainBinding.swapSketches.setEnabled(false);
                }
            }
        });

        //notes buttons state
        activityMainBinding.swapNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityMainBinding.swapNotes.isChecked()){
                    setButtonsVisibility(2);
                }
                else{
                    setButtonsVisibility(3);
                }
            }
        });

        //hide sketches button
        activityMainBinding.swapSketches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityMainBinding.swapSketches.isChecked()){
                    setButtonsVisibility(4);
                }
                else{
                    setButtonsVisibility(5);
                }
            }
        });


        //background modes
        activityMainBinding.enableWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityMainBinding.enableWall.isChecked()){
                    activityMainBinding.changeWall.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Stopping Background service", Toast.LENGTH_SHORT).show();
                    MainActivity.this.stopService(axpService);
                }
                else{
                    activityMainBinding.changeWall.setEnabled(false);
                    Toast.makeText(MainActivity.this, "Starting Background service", Toast.LENGTH_SHORT).show();
                    MainActivity.this.startService(axpService);
                }
            }
        });
        activityMainBinding.changeWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityMainBinding.changeWall.isChecked()){
                    changeBackground(1);
                }
                else{
                    changeBackground(0);
                }
            }
        });
        activityMainBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AXPPanel.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_start:
                initService();
                break;
            case R.id.hide_progress:
                hideProgressView();
                break;
        }
    }

    private void initService() {
        Intent intent = new Intent();
        intent.setAction("START_AXP_PROJECT");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
        Toast.makeText(this, "Starting AXP Service...", Toast.LENGTH_SHORT).show();
    }

    void setButtonsVisibility(int state){
        Intent intent = new Intent();
        intent.setAction("SET_BUTTONS_VISIBILITY");
        intent.putExtra("State", state);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }

    void hideProgressView(){
        Intent intent = new Intent();
        intent.setAction("HIDE_PROGRESS_VIEW");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }

    void changeBackground(int configuration){
        Intent intent = new Intent();
        intent.setAction("CHANGE_WALL");
        intent.putExtra("Value", configuration);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }



}
