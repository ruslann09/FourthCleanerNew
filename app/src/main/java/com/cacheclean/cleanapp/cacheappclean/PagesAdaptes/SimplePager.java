package com.cacheclean.cleanapp.cacheappclean.PagesAdaptes;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cacheclean.cleanapp.cacheappclean.Frags.BatterySave;
import com.cacheclean.cleanapp.cacheappclean.Frags.CPUCool;
import com.cacheclean.cleanapp.cacheappclean.Frags.JunkClean;
import com.cacheclean.cleanapp.cacheappclean.Frags.PhoneBoost;

public class SimplePager extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public SimplePager(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BatterySave tab3 = new BatterySave();
                return tab3;
            case 1:
                JunkClean tab2 = new JunkClean();
                return tab2;
            case 2:
                PhoneBoost tab1 = new PhoneBoost();
                return tab1;
            case 3:
                CPUCool tab4 = new CPUCool();
                return tab4;
//            case 4:
//                 Payed tab5 = new Payed();
//                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
