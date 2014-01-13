/*
 * Activity launched by clicking on Menu uv item 
 * Launch layout activity_uv
 * 
 */

package fr.utt.topuv.activity;

import fr.utt.topuv.R;
import fr.utt.topuv.constant.IntentConstants;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class UvActivity extends FragmentActivity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uv);

        // Specify that the Home button should show an "Up" caret, indicating that touching the
        // button will take the user one step up in the application's hierarchy.
        getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
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
