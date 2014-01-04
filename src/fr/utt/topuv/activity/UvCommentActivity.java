/*
 * Activity launched by clicking on add comment in specific UV
 * Launch layout activity_uv_comment
 * 
 */

package fr.utt.topuv.activity;

import fr.utt.topuv.R;
import android.os.Bundle;
import android.app.Activity;

public class UvCommentActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_uv_comment);
	}
}
