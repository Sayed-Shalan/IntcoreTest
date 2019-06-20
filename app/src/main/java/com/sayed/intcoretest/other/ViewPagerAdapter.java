package com.sayed.intcoretest.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentsList;

    public ViewPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragmentsList) {
        super(fragmentManager);
        this.fragmentsList=fragmentsList;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}
