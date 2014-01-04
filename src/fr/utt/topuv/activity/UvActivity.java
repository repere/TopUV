/*
 * Activity launched by clicking on Menu uv item 
 * Launch layout activity_uv
 * 
 */

package fr.utt.topuv.activity;

import fr.utt.topuv.R;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
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
                // This is called when the Home (Up) button is pressed in the action bar.
                // Create a simple intent that starts the hierarchical parent activity and
                // use NavUtils in the Support Package to ensure proper handling of Up.
                Intent upIntent = new Intent(this, MenuActivity.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) 
                {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } 
                
                else 
                {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
