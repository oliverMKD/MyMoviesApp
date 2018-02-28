package com.oliver.mymovies.adapteri;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//import com.oliver.mymovies.fragmenti.Popular;

import java.util.ArrayList;

/**
 * Created by Oliver on 2/6/2018.
 */

public class VPagerAdapter extends FragmentPagerAdapter {
//    Zborovi zborovi = new Zborovi();

    ArrayList<Fragment> fragmenti = new ArrayList<Fragment>();
    ArrayList<String> titles = new ArrayList<>();

    public void dodadiFragment (Fragment fragment, String titlovi){
        fragmenti.add(fragment);
        titles.add(titlovi);




    }

    public VPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmenti.get(position);
    }

    @Override
    public int getCount() {
        return fragmenti.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {return titles.get(position);}



}
