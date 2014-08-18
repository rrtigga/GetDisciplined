package com.ibc.android.demo.appslist.app;

import android.content.SharedPreferences;

import java.util.List;

/**
 * Created by Spicycurryman on 8/15/14.
 */
public class AppHandler {

    private List<AppState> lastInstalledApp;
    //private Gson gson;


    public void retrieve(SharedPreferences pref) {

        // parse the gson from shared preferences
        // create the lastInstalledApp list
        String jsonState = pref.getString("APP_STATE", null);
      //  if (jsonState != null)
           // lastInstalledApp = gson.fromJson(jsonState, lastInstalledApp.getClass());
    }

    public void store(SharedPreferences pref) {

        // store the data to shared preferences
    }


    public List<AppState> getAppList() {

        return lastInstalledApp;
    }

    public static class AppState {

        protected boolean lastCheckbox;
        protected String name;

        public boolean isLastCheckbox() {
            return lastCheckbox;
        }

        public void setLastCheckbox(boolean lastCheckbox) {
            this.lastCheckbox = lastCheckbox;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }

}