package com.ibc.android.demo.appslist.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.spicycurryman.getdisciplined10.app.R;

import java.util.List;

//

public class ApkAdapter extends BaseAdapter {

    //Pastebin link:  http://pastebin.com/LGRicg4U , http://pastebin.com/c4WfmhMK , http://pastebin.com/gFuuM4dY, http://pastebin.com/4Q7EP9G4
    // http://pastebin.com/Te2g072w,  http://pastebin.com/NLT5iUiA ,

    SharedPreferences sharedPrefs;
    List<PackageInfo> packageList;
    Activity context;
    PackageManager packageManager;
    boolean[] itemChecked;

    String PACKAGE_NAME;

    public ApkAdapter(Activity context, List<PackageInfo> packageList,
                      PackageManager packageManager) {
        super();
        this.context = context;
        this.packageList = packageList;
        this.packageManager = packageManager;
        itemChecked = new boolean[packageList.size()];

    }


    private class ViewHolder {
        TextView apkName;
        CheckBox ck1;
        TextView packageName;
    }

    public int getCount() {
        return packageList.size();
    }

    public Object getItem(int position) {
        return packageList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.installed_apps, null);
            holder = new ViewHolder();

            holder.apkName = (TextView) convertView
                    .findViewById(R.id.appname);
            holder.ck1= (CheckBox)convertView
                    .findViewById(R.id.checkBox1);
            holder.packageName = (TextView) convertView.findViewById(R.id.app_package);



            convertView.setTag(holder);
            //holder.ck1.setTag(packageList.get(position));

        } else {

            holder = (ViewHolder) convertView.getTag();
        }



        // ViewHolder holder = (ViewHolder) convertView.getTag();
        final PackageInfo packageInfo = (PackageInfo) getItem(position);



        Drawable appIcon = packageManager
                .getApplicationIcon(packageInfo.applicationInfo);



        // Make sure to define it again!
        PACKAGE_NAME = packageInfo.packageName;


        final String appName = packageManager.getApplicationLabel(
                packageInfo.applicationInfo).toString();
        appIcon.setBounds(0, 0, 80, 80);
        holder.apkName.setCompoundDrawables(appIcon, null, null, null);
        holder.apkName.setCompoundDrawablePadding(15);
        holder.apkName.setText(appName);
        //holder.packageName.setText(PACKAGE_NAME);


        holder.ck1.setChecked(false);


        if (itemChecked[position])
            holder.ck1.setChecked(true);
        else
            holder.ck1.setChecked(false);





        // CHANGE UP EVERYTHING! MAKE THIS SHIT WORK, TIGGA!


        for(int i= 0; i<packageList.size(); i++){
            PACKAGE_NAME = packageInfo.packageName;
            Log.d("lol", PACKAGE_NAME);

            sharedPrefs = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);


            holder.ck1.setChecked(sharedPrefs.getBoolean(PACKAGE_NAME,false));


        }

        holder.ck1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                SharedPreferences.Editor editor = context.getSharedPreferences(packageInfo.packageName, Context.MODE_PRIVATE).edit();

                if (holder.ck1.isChecked()) {
                    itemChecked[position] = true;
                    holder.ck1.setChecked(true);
                    editor.putBoolean(packageInfo.packageName, true);

                    editor.apply();

                } else {
                    itemChecked[position] = false;
                    holder.ck1.setChecked(false);
                    editor.putBoolean(packageInfo.packageName, false);

                    editor.apply();

                }

            }


        });



        return convertView;

    }






}