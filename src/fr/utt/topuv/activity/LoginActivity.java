/* 
 * First activity launched by the start of application 
 * Start layout activity_login
 * 
 */

package fr.utt.topuv.activity;

import fr.utt.topuv.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class LoginActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}