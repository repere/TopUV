/*
 * Activity launched by clicking on add comment in specific UV
 * Launch layout activity_uv_comment
 * 
 */

package fr.utt.topuv.activity;

import fr.utt.topuv.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UvCommentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uv_comment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.uv_comment, menu);
		return true;
	}

}
