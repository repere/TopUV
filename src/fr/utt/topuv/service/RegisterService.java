package fr.utt.topuv.service;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;
import fr.utt.topuv.R;
import fr.utt.topuv.activity.LoginActivity;
import fr.utt.topuv.constant.WebServiceConstants;

public class RegisterService extends CustomAsyncTask<String, Void, String>
{        
	// Progress Dialog
    private ProgressDialog pDialog;
    
    // User info
 	private String last_name;
 	private String first_name;       
 	private String email;
 	private String login;
 	private String password;
	
	public RegisterService(Activity activity) 
	{
		super(activity);
	}
	
	@Override
	protected void onPreExecute() 
	{
		super.onPreExecute();
		showProgressDialog();
	}
	
	@Override
	protected void onActivityDetached() 
	{
		if (pDialog != null) 
		{
			pDialog.dismiss();
			pDialog = null;
		}
	}
	
	@Override
	protected void onActivityAttached() 
	{
		showProgressDialog();
	}
	
	private void showProgressDialog() 
	{
		pDialog = new ProgressDialog(mActivity);
        pDialog.setTitle("Enregistrement de votre compte");
        pDialog.setMessage("Traitement en cours...");
        pDialog.setIcon(R.drawable.ic_action_new_account);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
	}
	
	@Override
	protected void onPostExecute(String isSentOrNot) 
	{
		super.onPostExecute(isSentOrNot);

		if (mActivity != null) 
		{
			registerSendStatus(isSentOrNot);
	        pDialog.dismiss();
		}
	}
	
	private void registerSendStatus(String status)
	{
		String resultToGive;
		
		if(status.equals("2"))
	    {
	    	resultToGive = mActivity.getString(R.string.register_confirm);
	    	
	    	Intent intent = new Intent(mActivity, LoginActivity.class);

            mActivity.startActivity(intent);
	    }
		
	    else if (status.equals("1"))
	    {
	    	resultToGive = mActivity.getString(R.string.login_already_register);
	    }
		
	    else
	    {
	    	resultToGive = mActivity.getString(R.string.register_error);
	    }
		
		Toast.makeText(mActivity, resultToGive, Toast.LENGTH_LONG).show();
	}
	
	@Override
    protected String doInBackground(String... params)
    {
		last_name = params[0];
		first_name = params[1];       
        email = params[2];
        login = params[3];
        password = params[4];
        
        // Base url
        String url = WebServiceConstants.REGISTER.URL;

        // Query string
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.REGISTER.LAST_NAME, last_name));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.REGISTER.FIRST_NAME, first_name));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.REGISTER.EMAIL, email));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.REGISTER.LOGIN, login));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.REGISTER.PASSWORD, password));
        
        MakeHttpPostRequest makeHttopPostRequest = new MakeHttpPostRequest();
        
        JSONObject jsonObject = makeHttopPostRequest.execute(url, nameValuePairs);
        
        try 
        {
			String result = jsonObject.getString(WebServiceConstants.REGISTER.SUCCESS).toString();
			return result;
		} 
        
        catch (JSONException e) 
        {
			e.printStackTrace();
		}
        
        return null;
    }
}