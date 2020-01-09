package com.joeanakbar.fp.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.joeanakbar.fp.R;
import com.joeanakbar.fp.receiver.ReminderReceiver;

public class SettingFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    Switch dailySwitch, releaseSwitch;
    SharedPreferences dailyAlarmPref, releaseAlarmPref;
    private ReminderReceiver reminderReceiver = new ReminderReceiver();

    private static final String DAILY = "daily";
    private static final String RELEASE = "release";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dailySwitch = view.findViewById(R.id.dailyReminderSwitch);
        releaseSwitch = view.findViewById(R.id.releaseReminderSwitch);

        dailySwitch.setOnCheckedChangeListener(this);
        releaseSwitch.setOnCheckedChangeListener(this);

        dailyAlarmPref = this.getActivity().getSharedPreferences(DAILY, Context.MODE_PRIVATE);
        dailySwitch.setChecked(dailyAlarmPref.getBoolean(DAILY, false));

        releaseAlarmPref = this.getActivity().getSharedPreferences(RELEASE, Context.MODE_PRIVATE);
        releaseSwitch.setChecked(releaseAlarmPref.getBoolean(RELEASE, false));

        Button btnChangeLanguage = view.findViewById(R.id.btnLanguage);
        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(change);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case (R.id.dailyReminderSwitch):
                if (isChecked) {
                    String time = "07:00";
                    reminderReceiver.setRepeatingAlarm(getActivity(), ReminderReceiver.TYPE_DAILY_REMINDER, time, getString(R.string.daily_reminder_message));
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(DAILY, Context.MODE_PRIVATE).edit();
                    editor.putBoolean(DAILY, true);
                    editor.apply();
                } else {
                    reminderReceiver.cancelAlarm(getActivity(), ReminderReceiver.TYPE_DAILY_REMINDER);
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(DAILY, Context.MODE_PRIVATE).edit();
                    editor.putBoolean(DAILY, false);
                    editor.apply();
                }
                break;
            case (R.id.releaseReminderSwitch):
                if (isChecked) {
                    String time = "08:00";
                    reminderReceiver.setRepeatingAlarm(getContext(), ReminderReceiver.TYPE_RELEASE_TODAY_REMINDER, time, "");
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(RELEASE, Context.MODE_PRIVATE).edit();
                    editor.putBoolean(RELEASE, true);
                    editor.apply();
                } else {
                    reminderReceiver.cancelAlarm(getContext(), ReminderReceiver.TYPE_RELEASE_TODAY_REMINDER);
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(RELEASE, Context.MODE_PRIVATE).edit();
                    editor.putBoolean(RELEASE, false);
                    editor.apply();
                }
                break;
        }
    }
}
