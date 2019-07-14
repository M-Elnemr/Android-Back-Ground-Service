package com.example.backgroundserviceneverend;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

public class SensorService extends Service {

    public int counter = 0;
    private Timer timer;
    private TimerTask timerTask;
    long oldTime = 0;
    Activity activity;
    private String CHANNEL_ID = "1";
    private boolean mRunning;



    public SensorService(Activity activity) {

        this.activity = activity;
        LogUtil.vebose("i am here");
    }

    public SensorService() {}

    @Override
    public void onCreate() {
        super.onCreate();
        mRunning = false;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mRunning) {
            mRunning = true;
            LogUtil.vebose("onStartCommand: started");

            startTimer();
        }
        
        return START_STICKY;
    }

    public void startTimer() {

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {

                LogUtil.vebose("in timer" + counter++);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle("hello")
                        .setContentText("this is a text")
                        .setPriority(Notification.PRIORITY_MAX);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificationChannel();
                }
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                notificationManager.notify(0, builder.build());

            }
        };
        timer.schedule(timerTask, 5000, 5000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.vebose("destroy service");

        Intent cast = new Intent(this, AutoLoadBroadcast.class);
        sendBroadcast(cast);
        stoptimertask();
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "1";
            String description = "12";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
