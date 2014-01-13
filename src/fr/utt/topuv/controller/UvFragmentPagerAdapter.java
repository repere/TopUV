/*
 * Pager adapter for the view pager
 */

package fr.utt.topuv.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import fr.utt.topuv.R;
import fr.utt.topuv.constant.IntentConstants;

public class UvFragmentPagerAdapter extends FragmentPagerAdapter 
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
    	ListFragment fragment = new ListUvController();
    	Bundle bundle_uv = new Bundle();
    	
    	switch (i) 
    	{    
    		case 0 :
    			bundle_uv.putString(IntentConstants.CODE, "CS");
    			fragment.setArguments(bundle_uv);
    	        return fragment;
	    	case 1 :
	    		bundle_uv.putString(IntentConstants.CODE, "TM");
    			fragment.setArguments(bundle_uv);
    	        return fragment;
	    	case 2 :
	    		bundle_uv.putString(IntentConstants.CODE, "EC");
    			fragment.setArguments(bundle_uv);
    	        return fragment;
	    	case 3 :
	    		bundle_uv.putString(IntentConstants.CODE, "ME");
    			fragment.setArguments(bundle_uv);
    	        return fragment;
    		case 4 :
    			bundle_uv.putString(IntentConstants.CODE, "CT");
    			fragment.setArguments(bundle_uv);
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
