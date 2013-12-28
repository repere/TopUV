package fr.utt.topuv.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import fr.utt.top.fragment.DummySectionFragment;
import fr.utt.topuv.R;
import fr.utt.topuv.controller.ListUvController;

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
    	Fragment fragment = new ListUvController();
        Bundle args = new Bundle();
        
    	switch (i) 
    	{    
	    	default:
	    		Fragment fm = new DummySectionFragment();
				args.putString(DummySectionFragment.ARG_SECTION_TYPE, "CS");
				fm.setArguments(args);
	            return fm;
    		/*case 0 :
	    		args.putString(ListUvController.ARG_SECTION_TYPE, "CS");
	            fragment.setArguments(args);
	            return fragment;
	    	case 1 :
	    		args.putString(ListUvController.ARG_SECTION_TYPE, "TM");
	            fragment.setArguments(args);
	            return fragment;
	    	case 2 :
	    		args.putString(ListUvController.ARG_SECTION_TYPE, "EC");
	            fragment.setArguments(args);
	            return fragment;
	    	case 3 :
	    		args.putString(ListUvController.ARG_SECTION_TYPE, "ME");
	            fragment.setArguments(args);
	            return fragment;
    		case 4 :
        		args.putString(ListUvController.ARG_SECTION_TYPE, "CT");
                fragment.setArguments(args);
                return fragment;*/
        }
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
