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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.SharedPreferences.Editor;

import com.spicycurryman.getdisciplined10.app.R;

import java.util.List;

public class ApkAdapter extends BaseAdapter  {


    //Pastebin link:  http://pastebin.com/LGRicg4U , http://pastebin.com/c4WfmhMK http://pastebin.com/gFuuM4dY

    //SharedPreferences sharedPrefs;
    Editor editor;
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




    static class ViewHolder {
        TextView apkName;
        CheckBox ck1;
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

        SharedPreferences sharedPrefs = context.getSharedPreferences("apps", Context.MODE_PRIVATE);

        final ViewHolder holder;

        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.installed_apps, null);
            holder = new ViewHolder();

            holder.apkName = (TextView) convertView
                    .findViewById(R.id.appname);
            holder.ck1 = (CheckBox) convertView
                    .findViewById(R.id.checkBox1);

            convertView.setTag(holder);
            //holder.ck1.setTag(packageList.get(position));

        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        // ViewHolder holder = (ViewHolder) convertView.getTag();
        PackageInfo packageInfo = (PackageInfo) getItem(position);


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


        Log.d("just loaded??", appName);


        Log.d("just loaded 2?", appName);

        editor = sharedPrefs.edit();


        holder.ck1.setChecked(sharedPrefs.getBoolean(appName +position, false));

        //SharedPreferences.Editor editor = context.getSharedPreferences(appName, Context.MODE_PRIVATE).edit();

        holder.ck1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                editor.putBoolean(appName + position, holder.ck1.isChecked());
                editor.commit();
            }


        });
        return convertView;

    }
}