/*
 * Activity launch after login connection
 * Launch layout activity_menu
 * Include view pager
 */

package fr.utt.topuv.activity;

import fr.utt.topuv.R;
import fr.utt.topuv.constant.WebServiceConstants;
import fr.utt.topuv.controller.UvFragmentPagerAdapter;
import fr.utt.topuv.service.CustomApplication;
import fr.utt.topuv.service.GetAllService;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuActivity extends Activity implements ActionBar.TabListener 
{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
	UvFragmentPagerAdapter fmPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        fmPagerAdapter = new UvFragmentPagerAdapter(getFragmentManager(), this.getApplicationContext());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setDisplayHomeAsUpEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(fmPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < fmPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(fmPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) 
    {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    
		Intent intent;
	    switch (item.getItemId()) 
	    {  
	    	case R.id.action_refresh:
	    		String url_uv = WebServiceConstants.UVS.URL;
	    		String url_comment = WebServiceConstants.COMMENTS.URL;
	    		
	    		GetAllService getAllService = new GetAllService(MenuActivity.this);
	        	getAllService.execute(url_uv, url_comment);
	        	
        		return true;
        		
	    	case R.id.action_search:
	    		intent = new Intent(this, SearchActivity.class);
                
	        	this.startActivity(intent);
	        	
        		return true;
        		
	    	case R.id.action_topuv:
    			intent = new Intent(this, TopUvActivity.class);
                
	        	this.startActivity(intent);
	        	
        		return true;
	    	
	    	default:
	            return super.onOptionsItemSelected(item);
	    }
	}
    
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
    {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
    {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
    {
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) 
    {
    	super.onSaveInstanceState(outState);
    	
    	((CustomApplication) getApplication()).detach(this);
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) 
    {
    	super.onRestoreInstanceState(savedInstanceState);
    	
    	((CustomApplication) getApplication()).attach(this);
    }
}
