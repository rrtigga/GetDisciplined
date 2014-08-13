package info.androidhive.tabsswipe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.spicycurryman.getdisciplined10.app.InstalledAppActivity;


public class TabsPagerAdapter extends FragmentPagerAdapter implements Runnable {
//...
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new InstalledAppActivity();



        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 1;
    }

    @Override
    public void run() {

    }
}