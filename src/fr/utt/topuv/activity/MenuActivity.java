/*
 * Activity launch after login connection
 * Launch layout activity_menu
 * Populate firstly list with CS UV and makes the link bar active
 */

package fr.utt.topuv.activity;

import fr.utt.topuv.R;
import fr.utt.topuv.controller.ListUvController;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
				
		//Load list of CS uv by default
		//ListUvController listUv = (ListUvController) getFragmentManager().findFragmentById(R.id.list_fragment);
		//listUv.getListUv();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
