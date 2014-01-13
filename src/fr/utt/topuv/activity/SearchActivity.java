package fr.utt.topuv.activity;

import fr.utt.topuv.R;
import fr.utt.topuv.constant.IntentConstants;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class SearchActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() 
	{

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case android.R.id.home:
				Intent upIntent = new Intent(this, MenuActivity.class);
                int idUser = this.getIntent().getIntExtra(IntentConstants.ID_USER, 0);
                upIntent.putExtra(IntentConstants.ID_USER, idUser);
                
                NavUtils.navigateUpTo(this, upIntent);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
