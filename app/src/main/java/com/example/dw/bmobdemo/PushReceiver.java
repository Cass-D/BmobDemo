package com.example.dw.bmobdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.bmob.push.PushConstants;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by dw on 2017-3-11.
 */

public class PushReceiver extends BroadcastReceiver {
    String message="";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            String msg=intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
            JSONTokener jsonTokener=new JSONTokener(msg);
            try{
                JSONObject object=(JSONObject)jsonTokener.nextValue();
                message=object.getString("alert");
            }catch (JSONException e){
                e.printStackTrace();
            }
            NotificationManager manager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            Notification notification=new Notification(R.drawable.ic_launcher,"testbmob", System.currentTimeMillis());
            //notification.setLatestEventInfo(context, "This is content title", "This iscontent text", null);
            manager.notify(R.drawable.ic_launcher,notification);
        }
    }
}
