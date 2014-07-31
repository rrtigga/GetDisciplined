package com.spicycurryman.getdisciplined10.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomList_Activity extends Fragment
        implements OnItemClickListener {




    PackageManager packageManager;
    ListView apkList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.user_installed, container, false);
        packageManager = getActivity().getPackageManager();


		/*To filter out System apps*/

        apkList = (ListView) rootView.findViewById(R.id.applist);

        new LoadApplications(getActivity().getApplicationContext()).execute();


        return rootView;
    }

    /**
     * Return whether the given PackageInfo represents a system package or not.
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

    private boolean isSystemPackage1(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) ? false
                : true;
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    private class LoadApplications extends AsyncTask<Void, Void, Void> {


        List<PackageInfo> packageList1 = new ArrayList<PackageInfo>();

        public LoadApplications(Context context){
            Context mContext = context;
        }




        @Override
        protected Void doInBackground(Void... params) {

            List<PackageInfo> packageList = packageManager
                    .getInstalledPackages(PackageManager.GET_PERMISSIONS);





            for(PackageInfo pi : packageList) {
                boolean b = isSystemPackage(pi);
                boolean c = isSystemPackage1(pi);

                if(!b || !c ) {

                    if (BlockActivity.blacklist.contains(pi.packageName))
                    {
                        packageList1.add(pi);
                    }


                }
            }

            //sort by application name

            final PackageItemInfo.DisplayNameComparator comparator = new PackageItemInfo.DisplayNameComparator(packageManager);

            Collections.sort(packageList1, new Comparator<PackageInfo>() {
                @Override
                public int compare(PackageInfo lhs, PackageInfo rhs) {
                    return comparator.compare(lhs.applicationInfo, rhs.applicationInfo);
                }
            });


            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
