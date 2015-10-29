package com.example.mac.alarm;

/**
 * Created by Mac on 9/23/2015.
 */
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static Ringtone ringtone;

    @Override
    public void onReceive(final Context context, Intent intent) {
        //this will update the UI with message

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        if (ringtone == null){
            ringtone = RingtoneManager.getRingtone(context, alarmUri);
        }

        if (intent.getBooleanExtra("extra", false)) {
            Log.v("ALARM IS RINGING", "ALARM IS RINGING");
            if(ringtone.isPlaying()){
                ringtone.stop();
            } else {
                AlarmActivity inst = AlarmActivity.instance();
                inst.setAlarmText("Alarm! Wake up! Wake up!");
                ringtone.play();
            }
        }


        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }

    public void stopRingtone(){
        ringtone.stop();
    }
}