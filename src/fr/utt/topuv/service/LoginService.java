/*
 * Ask DB to indicate if combination login/password is correct
 * 
 * 
 */


package fr.utt.topuv.service;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import fr.utt.topuv.R;
import fr.utt.topuv.activity.MenuActivity;
import fr.utt.topuv.constant.WebServiceConstants;
import fr.utt.topuv.model.User;

public class LoginService extends AsyncTask<String, Void, User>
{        
    //To create the Progress Dialog
    private Activity motherActivity;
    
    // Progress Dialog
    private ProgressDialog pDialog;
    
    private User currentUser;

    public LoginService(Activity activity) 
    {
        motherActivity = activity;
    }

        @Override
    protected void onPreExecute() 
    {
        super.onPreExecute();
        pDialog = new ProgressDialog(motherActivity);
        pDialog.setTitle("Récupération de votre compte");
        pDialog.setMessage("Connexion en cours...");
        pDialog.setIcon(R.drawable.ic_action_share);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
    }
        
    @Override
    protected void onPostExecute(User userConnected) 
    {      
        currentUser = userConnected;
        this.loginControl();
        pDialog.dismiss();
    }
        
    public void loginControl()
    {
        int success = currentUser.getSuccess();
        
        if(success == 1)
        {
            Intent intent = new Intent(motherActivity, MenuActivity.class);

            motherActivity.startActivity(intent);
        }
        
        else
        {
            Toast toast = Toast.makeText(motherActivity, R.string.connexion_error, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
    }
        
        @Override
    protected User doInBackground(String... params)
    {
        String login = params[0];
        String password = params[1];

        // Base url
        String url = WebServiceConstants.CONNEXION.URL;

        // Query string
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.CONNEXION.LOGIN, login));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.CONNEXION.PASSWORD, password));

        MakeHttpPostRequest makeHttopPostRequest = new MakeHttpPostRequest();
        
        JSONObject jsonObject = makeHttopPostRequest.execute(url, nameValuePairs);
        
        User userSelected = new User();
        
        try 
        {
			User.setId(jsonObject.getInt(WebServiceConstants.CONNEXION.ID_USER));
			userSelected.setSuccess(jsonObject.getInt(WebServiceConstants.CONNEXION.SUCCESS));
	        User.setFirstName(jsonObject.getString(WebServiceConstants.CONNEXION.FIRST_NAME));
	        User.setLastName(jsonObject.getString(WebServiceConstants.CONNEXION.LAST_NAME));
	        
	        return userSelected;
		} 
        
        catch (JSONException e) 
        {
			e.printStackTrace();
		}
        
        return null;
    }        
}