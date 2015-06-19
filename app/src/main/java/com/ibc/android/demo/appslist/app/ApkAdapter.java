package com.ibc.android.demo.appslist.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.spicycurryman.getdisciplined10.app.R;

import java.util.HashSet;
import java.util.List;

//

public class ApkAdapter extends BaseAdapter {

    SharedPreferences sharedPrefs;
    SharedPreferences sharedPrefsapp;

    SharedPreferences endTimerPreferences;
    long timerends;

    List<PackageInfo> packageList;
    Activity context;
    PackageManager packageManager;
    boolean[] itemChecked;
    HashSet checked;

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
            holder.apkName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/miso-bold.otf"));

            holder.ck1= (CheckBox)convertView
                    .findViewById(R.id.checkBox1);
            holder.packageName = (TextView) convertView.findViewById(R.id.app_package);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }
        final PackageInfo packageInfo = (PackageInfo) getItem(position);

        Drawable appIcon = packageManager
                .getApplicationIcon(packageInfo.applicationInfo);


        PACKAGE_NAME = packageInfo.packageName;


        final String appName = packageManager.getApplicationLabel(
                packageInfo.applicationInfo).toString();
        appIcon.setBounds(0, 0, 80, 80);
        holder.apkName.setCompoundDrawables(appIcon, null, null, null);
        holder.apkName.setCompoundDrawablePadding(15);
        holder.apkName.setText(appName);
        holder.ck1.setChecked(false);

        if (itemChecked[position])
            holder.ck1.setChecked(true);
        else
            holder.ck1.setChecked(false);

        checked = new HashSet();

        PACKAGE_NAME = packageInfo.packageName;
        sharedPrefs = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        sharedPrefsapp = context.getSharedPreferences("appdb", Context.MODE_PRIVATE);
        holder.ck1.setChecked(sharedPrefs.getBoolean(PACKAGE_NAME,false));
        endTimerPreferences = context.getSharedPreferences("endservice", Context.MODE_PRIVATE);
        timerends= endTimerPreferences.getLong("endservice", 0);


        if(System.currentTimeMillis() < timerends ) {

            if(holder.ck1.isChecked()){
                holder.ck1.setClickable(false);
                holder.ck1.setEnabled(false);
            }
            else{
                holder.ck1.setClickable(true);
                holder.ck1.setEnabled(true);

            }
        }
            holder.ck1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE).edit();
                SharedPreferences.Editor editorapp = context.getSharedPreferences("appdb", Context.MODE_PRIVATE).edit();

                if (holder.ck1.isChecked()) {

                    itemChecked[position] = true;
                    holder.ck1.setChecked(true);
                    editor.putBoolean(packageInfo.packageName, true);
                    editorapp.putString(packageInfo.packageName, packageInfo.packageName);
                    editor.apply();
                    editorapp.apply();

                } else {
                    itemChecked[position] = false;
                    holder.ck1.setChecked(false);
                    editor.putBoolean(packageInfo.packageName, false);
                    editorapp.remove(packageInfo.packageName);
                    editor.apply();
                    editorapp.apply();
                }
            }
        });

        return convertView;

    }




}