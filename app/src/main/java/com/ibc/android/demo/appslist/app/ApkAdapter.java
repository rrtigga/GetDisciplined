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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.spicycurryman.getdisciplined10.app.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//

public class ApkAdapter extends BaseAdapter implements Filterable {

    //Pastebin link:  http://pastebin.com/LGRicg4U , http://pastebin.com/c4WfmhMK , http://pastebin.com/gFuuM4dY, http://pastebin.com/4Q7EP9G4
    // http://pastebin.com/Te2g072w,  http://pastebin.com/NLT5iUiA ,

    SharedPreferences sharedPrefs;
    SharedPreferences sharedPrefsapp;

    List<PackageInfo> packageList;
    TextView appnamestyle;

    Activity context;
    PackageManager packageManager;
    boolean[] itemChecked;
    HashSet checked;
    Filter mFilter;

    String PACKAGE_NAME;

    TextView appname;

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
            holder.apkName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/miso.otf"));

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





        checked = new HashSet();

        PACKAGE_NAME = packageInfo.packageName;
        //Log.d("just here: ", PACKAGE_NAME);

        sharedPrefs = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        sharedPrefsapp = context.getSharedPreferences("appdb", Context.MODE_PRIVATE);





        holder.ck1.setChecked(sharedPrefs.getBoolean(PACKAGE_NAME,false));







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

                    // sharedPrefs = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);


                } else {
                    itemChecked[position] = false;
                    holder.ck1.setChecked(false);
                    editor.putBoolean(packageInfo.packageName, false);
                    editorapp.remove(packageInfo.packageName);


                    editor.apply();
                    editorapp.apply();
                    //sharedPrefs = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);




                }

            }



        });





        return convertView;

    }


    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ItemsFilter();
        }
        return mFilter;
    }

    private class ItemsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            // TODO Auto-generated method stub

            List<PackageInfo> packageList_2 = packageList;
            String prefixString = prefix.toString().toLowerCase();
            FilterResults results = new FilterResults();
            ArrayList<PackageInfo> FilteredList = new ArrayList<PackageInfo>();

            if (prefix == null || prefix.length() == 0) {
                results.values = packageList_2;
                results.count = packageList_2.size();
                return results;
            }
            for (int i = 0; i < packageList_2.size(); i++) {
                String filterText = prefix.toString().toLowerCase();
                try {
                    PackageInfo data = packageList_2.get(i);
                    if (data.applicationInfo
                            .loadLabel(context.getApplicationContext().getPackageManager())
                            .toString().toLowerCase().contains(filterText)) {
                        FilteredList.add(data);
                    } else if (data.packageName.contains(filterText)) {
                        FilteredList.add(data);
                    }
                } catch (Exception e) {
                    Toast.makeText(context.getApplicationContext(),
                            "exception e" + e.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
            results.values = FilteredList;
            results.count = FilteredList.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            Toast.makeText(context.getApplicationContext(),"result-0 "+results.count,
                    Toast.LENGTH_SHORT).show();
            packageList = (List<PackageInfo>) results.values;
            notifyDataSetChanged();

        }
    }
}