package com.spicycurryman.getdisciplined10.app;

import android.app.ProgressDialog;
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

import com.ibc.android.demo.appslist.app.ApkAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class InstalledAppActivity extends Fragment
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


// Don't need in Fragment
/*@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.block, menu);
   // super.onCreateOptionsMenu(menu,inflater);
}*/

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //ApkAdapter apkAdapter=(ApkAdapter)apkList.getAdapter();

    }


    private class LoadApplications extends AsyncTask<Void, Void, Void> {

        Context mContext;

        private ProgressDialog pDialog;
        List<PackageInfo> packageList1 = new ArrayList<PackageInfo>();

        public LoadApplications(Context context){
            Context mContext = context;
        }




        @Override
        protected Void doInBackground(Void... params) {

            List<PackageInfo> packageList = packageManager
                    .getInstalledPackages(PackageManager.GET_PERMISSIONS);


          /*  List<ApplicationInfo> list = mContext.getPackageManager().getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);


            for(int n = 0;n<list.size();n++){
                if ((list.get(n).flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP))
            }*/


            for(PackageInfo pi : packageList) {
                boolean b = isSystemPackage(pi);
                boolean c = isSystemPackage1(pi);

                if(!b || !c ) {
                    packageList1.add(pi);
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
        protected void onPreExecute() {
            pDialog = new ProgressDialog(InstalledAppActivity.this.getActivity());
            pDialog.setMessage("Loading your apps...");
            pDialog.show();

        }

        @Override
        protected void onPostExecute(Void result) {

            apkList.setAdapter(new ApkAdapter(getActivity(), packageList1, packageManager));

            if (pDialog.isShowing()){
                pDialog.dismiss();

            }


            super.onPostExecute(result);
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



}