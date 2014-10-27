package com.spicycurryman.getdisciplined10.app;

import android.app.Application;

/**
 * Created by Spicycurryman on 10/26/14.
 */
public class ApplicationCheck extends Application {

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;
}