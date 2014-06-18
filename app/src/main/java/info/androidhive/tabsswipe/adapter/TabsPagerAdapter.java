package info.androidhive.tabsswipe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.spicycurryman.getdisciplined10.app.CustomList_Activity;
import com.spicycurryman.getdisciplined10.app.InstalledAppActivity;
import com.spicycurryman.getdisciplined10.app.Pre_InstalledApp_Activity;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new InstalledAppActivity();
            case 1:
                // Games fragment activity
                return new Pre_InstalledApp_Activity();
            case 2:
                // Movies fragment activity
                return new CustomList_Activity();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}