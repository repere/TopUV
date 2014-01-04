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
import android.os.AsyncTask;

import fr.utt.topuv.constant.WebServiceConstants;
import fr.utt.topuv.model.User;

public class LoginService extends AsyncTask<String, String, User>
{
		
	Activity motherActivity;
	// Progress Dialog
    private ProgressDialog pDialog;
	
	public LoginService(Activity activity) {
		motherActivity = activity;
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(motherActivity);
        pDialog.setTitle("Identification");
        pDialog.setMessage("Connexion en cours...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
	
	protected void onPostExecute(String file_url) {
        // dismiss the dialog once product deleted
        pDialog.dismiss();
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
        	httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        	
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        
	        String response = EntityUtils.toString(httpEntity);
	        
	        JSONObject jsonObject = new JSONObject(response);
	        
	        User userSelected = new User();
	        userSelected.setId(jsonObject.getString(WebServiceConstants.CONNEXION.ID_USER));
	        userSelected.setToken(jsonObject.getString(WebServiceConstants.CONNEXION.TOKEN));
	        
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