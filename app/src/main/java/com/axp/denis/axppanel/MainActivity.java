package com.axp.denis.axppanel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.axp.denis.axppanel.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.buttonStart.setOnClickListener(this);
        activityMainBinding.test.setOnClickListener(this);
        activityMainBinding.hideProgress.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_start:
                initService();
                break;
            case R.id.test:
                hideNotesButton();
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

    void hideNotesButton(){
        Intent intent = new Intent();
        intent.setAction("HIDE_NOTES_BUTTON");
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
