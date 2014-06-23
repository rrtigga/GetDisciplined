package com.spicycurryman.getdisciplined10.app;

import android.support.v4.app.Fragment;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ibc.android.demo.appslist.app.ApkAdapter;

import java.util.ArrayList;
import java.util.List;



public class CustomList_Activity extends Fragment
        implements OnItemClickListener {

    PackageManager packageManager;
    ListView apkList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.customactivity, container, false);
        packageManager = getActivity().getPackageManager();
        List<PackageInfo> packageList = packageManager
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);

        List<PackageInfo> packageList1 = new ArrayList<PackageInfo>();

		/*To filter out System apps*/
        for(PackageInfo pi : packageList) {
            boolean b = isSystemPackage(pi);
            if(!b) {
                packageList1.add(pi);
            }
        }
        apkList = (ListView) rootView.findViewById(R.id.applist);
        apkList.setAdapter(new ApkAdapter(getActivity(), packageList1, packageManager));

        apkList.setOnItemClickListener(this);

        return rootView;

    }

    /**
     * Return whether the given PackgeInfo represents a system package or not.
     * User-installed packages (Market or otherwise) should not be denoted as
     * system packages.
     *
     * @param pkgInfo
     * @return boolean
     */
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
                : false;
    }


// Don't need in Fragment
 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.block, menu);
        return true;
    }*/

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}