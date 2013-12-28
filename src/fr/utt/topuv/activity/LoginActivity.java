/* 
 * First activity launched by the start of application 
 * Start layout activity_login
 * 
 */

package fr.utt.topuv.activity;

import fr.utt.topuv.R;
import android.app.Activity;
import android.os.Bundle;

public class LoginActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
    }
}