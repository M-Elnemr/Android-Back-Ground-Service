package com.example.backgroundserviceneverend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoLoadBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent = new Intent(context, SensorService.class);
        context.startService(intent);
    }
}
