package com.spicycurryman.getdisciplined10.app;

/**
 * Created by Spicycurryman on 9/9/14.
 */

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * DeviceAdminDemo to enable, disable the options.
 * @author Prashant Adesara
 * */
public class DeviceAdmin extends DeviceAdminReceiver
{
    // implement onEnabled(), onDisabled(),
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    public void onEnabled(Context context, Intent intent) {};

    public void onDisabled(Context context, Intent intent) {};
}

