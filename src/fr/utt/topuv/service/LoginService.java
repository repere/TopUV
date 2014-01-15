/*
 * Ask DB to indicate if combination login/password is correct
 * 
 * 
 */


package fr.utt.topuv.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.CONNEXION.LOGIN, login));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.CONNEXION.PASSWORD, password)); 
        
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try
        {
        	HttpPost httpPost = new HttpPost(url);
        	httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
        	
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        
	        String response = EntityUtils.toString(httpEntity);
	        
	        JSONObject jsonObject = new JSONObject(response);
	        
	        User userSelected = new User();
	        User.setId(jsonObject.getInt(WebServiceConstants.CONNEXION.ID_USER));
	        userSelected.setSuccess(jsonObject.getInt(WebServiceConstants.CONNEXION.SUCCESS));
	        User.setFirstName(jsonObject.getString(WebServiceConstants.CONNEXION.FIRST_NAME));
	        User.setLastName(jsonObject.getString(WebServiceConstants.CONNEXION.LAST_NAME));
	        
	        return userSelected;
        }
        
	    catch(JSONException jsonException)
	    {
	
	    }
	    catch(ClientProtocolException clientProtocolException)
	    {
	
	    }
	    catch(IOException ioException)
	    {
	
	    }
        
	    return null;
    }	
}