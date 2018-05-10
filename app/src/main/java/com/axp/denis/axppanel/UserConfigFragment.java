package com.axp.denis.axppanel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

/**
 * User's panel for
 */

public class UserConfigFragment extends Fragment implements View.OnClickListener{
    Button batteryProgress, batteryPercentage;
    Switch enableManualBackground, switchBackground, showHideButtons, swapNotes, swapSketches, filterLauncher;
    Intent axpService;
    BroadcastWorker broadcastWorker;
    CardView cardView;


    public UserConfigFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_config_fragment, container, false);
        axpService = new Intent(getContext(), BackgroundScheduleService.class);
        broadcastWorker = new BroadcastWorker(getContext());
        //controls
        batteryProgress = view.findViewById(R.id.hide_progress);
        batteryPercentage = view.findViewById(R.id.hide_percentage);
        enableManualBackground = view.findViewById(R.id.enable_wall);
        switchBackground = view.findViewById(R.id.change_wall);
        showHideButtons = view.findViewById(R.id.buttons_visibility);
        swapNotes = view.findViewById(R.id.swap_notes);
        swapSketches = view.findViewById(R.id.swap_sketches);
        filterLauncher = view.findViewById(R.id.filter_launchers);
        cardView = view.findViewById(R.id.launch_themes);

        batteryProgress.setOnClickListener(this);
        batteryPercentage.setOnClickListener(this);
        showHideButtons.setOnClickListener(this);
        swapNotes.setOnClickListener(this);
        swapSketches.setOnClickListener(this);
        showHideButtons.setChecked(true);
        swapNotes.setChecked(false);
        swapSketches.setChecked(false);
        swapNotes.setEnabled(true);
        swapSketches.setEnabled(true);
        enableManualBackground.setChecked(false);
        switchBackground.setChecked(false);
        switchBackground.setEnabled(false);
        filterLauncher.setOnClickListener(this);
        filterLauncher.setChecked(false);
        filterLauncher.setEnabled(true);
        cardView.setOnClickListener(this);
        getContext().startService(axpService);

        filterLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filterLauncher.isChecked()){
                    broadcastWorker.filterLaunchers(true);
                }
                else{
                    broadcastWorker.filterLaunchers(false);
                }
            }
        });

        //set buttons visibility
        showHideButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showHideButtons.isChecked()){
                    broadcastWorker.setButtonsVisibility(1);
                    swapNotes.setEnabled(true);
                    swapSketches.setEnabled(true);
                }
                else{
                    broadcastWorker.setButtonsVisibility(0);
                    swapNotes.setEnabled(false);
                    swapSketches.setEnabled(false);
                }
            }
        });

        //notes buttons state
        swapNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swapNotes.isChecked()){
                    broadcastWorker.setButtonsVisibility(2);
                }
                else{
                    broadcastWorker.setButtonsVisibility(3);
                }
            }
        });

        //hide sketches button
        swapSketches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swapSketches.isChecked()){
                    broadcastWorker.setButtonsVisibility(4);
                }
                else{
                    broadcastWorker.setButtonsVisibility(5);
                }
            }
        });


        //background modes
        enableManualBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enableManualBackground.isChecked()){
                    switchBackground.setEnabled(true);
                    Toast.makeText(getContext(), "Stopping Background service", Toast.LENGTH_SHORT).show();
                    getContext().stopService(axpService);
                }
                else{
                    switchBackground.setEnabled(false);
                    Toast.makeText(getContext(), "Starting Background service", Toast.LENGTH_SHORT).show();
                    getContext().startService(axpService);
                }
            }
        });
        switchBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchBackground.isChecked()){
                    broadcastWorker.changeBackground(1);
                }
                else{
                    broadcastWorker.changeBackground(0);
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.hide_progress:
                broadcastWorker.hideProgressView();
                break;
            case R.id.hide_percentage:
                broadcastWorker.hidePercentage();
                break;
            case R.id.launch_themes:
                broadcastWorker.launchThemeViewer(getContext());
                break;
        }
    }
}
