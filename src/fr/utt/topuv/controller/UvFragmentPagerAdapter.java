package fr.utt.topuv.controller;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import fr.utt.topuv.R;
import fr.utt.topuv.fragment.ListCsFragment;
import fr.utt.topuv.fragment.ListCtFragment;
import fr.utt.topuv.fragment.ListEcFragment;
import fr.utt.topuv.fragment.ListMeFragment;
import fr.utt.topuv.fragment.ListTmFragment;

public class UvFragmentPagerAdapter extends FragmentStatePagerAdapter 
{

    Context TheContext;
	
	public UvFragmentPagerAdapter(FragmentManager fm, Context MainContext) 
	{
        super(fm);
        TheContext = MainContext;
    }

    @Override
    public Fragment getItem(int i) 
    {
    	ListFragment fragment;
        
    	switch (i) 
    	{    
    		case 0 :
    			fragment = new ListCsFragment();
    	        return fragment;
	    	case 1 :
	    		fragment = new ListTmFragment();
    	        return fragment;
	    	case 2 :
	    		fragment = new ListEcFragment();
    	        return fragment;
	    	case 3 :
	    		fragment = new ListMeFragment();
    	        return fragment;
    		case 4 :
    			fragment = new ListCtFragment();
    	        return fragment;
        }	
    	return null;
    }

    @Override
    public int getCount() 
    {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) 
    {
        switch (position) 
        {
            case 0:
                return TheContext.getString(R.string.title_section0);
            case 1:
                return TheContext.getString(R.string.title_section1);
            case 2:
                return TheContext.getString(R.string.title_section2);
            case 3:
                return TheContext.getString(R.string.title_section3);
            case 4:
                return TheContext.getString(R.string.title_section4);
        }
        return null;
    }
}
