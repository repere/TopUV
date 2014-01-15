/*
 * Activity launched by clicking on add comment in specific UV
 * Launch layout activity_uv_comment
 * 
 */

package fr.utt.topuv.activity;

import fr.utt.topuv.R;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;

public class UvCommentActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_uv_comment);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) 
	{
    	switch (item.getItemId()) 
        {
            case android.R.id.home:
            	Intent upIntent = new Intent(this, UvActivity.class);
            	
                NavUtils.navigateUpTo(this, upIntent);
				return true;
        }
        return super.onOptionsItemSelected(item);
	}
}
