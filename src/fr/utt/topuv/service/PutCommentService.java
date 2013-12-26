/*
 * Ask DB to add comment and rate on specific UV
 * 
 * 
 */

package fr.utt.topuv.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.utt.topuv.constant.WebServiceConstants;

public class PutCommentService extends AsyncTask<String, String, String>
{
	Activity motherActivity;
	// Progress Dialog
    private ProgressDialog pDialog;
	
	public PutCommentService(Activity activity) {
		motherActivity = activity;
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(motherActivity);
        pDialog.setMessage("Transfert des informations en cours...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
	
	protected void onPostExecute(String file_url) {
        // dismiss the dialog once product deleted
        pDialog.dismiss();
    }
	
	@Override
    protected String doInBackground(String... params)
    {
        String token = params[0];
        String comment = params[1];
        String mark = params[2];

        // Base uri
        String uri = WebServiceConstants.UV.URI;

        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.TOKEN, token));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.COMMENT, comment));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.MARK, mark));

        DefaultHttpClient httpClient = new DefaultHttpClient();

        try
        {
        	HttpPost httpPost = new HttpPost(uri);
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        
	        String response = EntityUtils.toString(httpEntity);
	        
	        JSONObject jsonObject = new JSONObject(response);
	        
	        return jsonObject.getString(WebServiceConstants.COMMENT.SUCCESS);
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