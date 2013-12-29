package fr.utt.topuv.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import fr.utt.topuv.R;

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
    	Fragment fragment = new ListUvController();
        Bundle args = new Bundle();
        
    	switch (i) 
    	{    
    		case 0 :
    			args.putString(ListUvController.TYPE_OF_ACTUAL_UV, "CS");
    	        fragment.setArguments(args);
    	        return fragment;
	    	case 1 :
	    		args.putString(ListUvController.TYPE_OF_ACTUAL_UV, "TM");
    	        fragment.setArguments(args);
    	        return fragment;
	    	case 2 :
	    		args.putString(ListUvController.TYPE_OF_ACTUAL_UV, "EC");
    	        fragment.setArguments(args);
    	        return fragment;
	    	case 3 :
	    		args.putString(ListUvController.TYPE_OF_ACTUAL_UV, "ME");
    	        fragment.setArguments(args);
    	        return fragment;
    		case 4 :
    			args.putString(ListUvController.TYPE_OF_ACTUAL_UV, "CT");
    	        fragment.setArguments(args);
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
