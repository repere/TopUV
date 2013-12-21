/*
 * Activity launched by clicking on Menu uv item 
 * Launch layout activity_uv
 * 
 */

package fr.utt.topuv.activity;

import fr.utt.topuv.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UvActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.uv, menu);
		return true;
	}

}
