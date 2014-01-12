/*
 * Ask DB to add comment and rate on specific UV
 * 
 * 
 */

package fr.utt.topuv.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import fr.utt.topuv.R;
import fr.utt.topuv.constant.WebServiceConstants;

public class PutCommentService extends AsyncTask<String, Void, String>
{
	//To create the Progress Dialog
	private Activity motherActivity;
	
	// THE MAGIC SENTENCE :p
	private String theMagicSentence = "sesameOuvreToi!";
	
	private String result = null;
	private String resultToGive;
	
	// Progress Dialog
    private ProgressDialog pDialog = null;   

	public PutCommentService(Activity activity) 
	{
		motherActivity = activity;
	}

	@Override
    protected void onPreExecute() 
	{
        super.onPreExecute();
        pDialog = new ProgressDialog(motherActivity);
        pDialog.setTitle("Envoi de la note");
        pDialog.setMessage("Traitement en cours...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
    }
	
	@Override
	protected void onPostExecute(String isSentOrNot) 
	{      
		// dismiss the dialog once product deleted
        pDialog.dismiss();
        
        commentSendStatus(isSentOrNot);
    }
	
	private void commentSendStatus(String status)
	{
		if(status.equals("2"))
	    {
	    	resultToGive = motherActivity.getString(R.string.comment_sent);
	    }
	    else if (status.equals("1"))
	    {
	    	resultToGive = motherActivity.getString(R.string.comment_already_sent);
	    }
	    else
	    {
	    	resultToGive = motherActivity.getString(R.string.comment_not_sent);
	    }
		
		Toast.makeText(motherActivity, resultToGive, Toast.LENGTH_LONG).show();
	}
	
	@Override
    protected String doInBackground(String... params)
    {
		String idUser = params[0];
		String code = params[1];       
        String comment = params[2];
        String note = params[3];

        //Convert comment to base64
        byte[] data = null;
		try 
		{
			data = comment.getBytes("UTF-8");
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String commentToBase64 = Base64.encodeToString(data, Base64.DEFAULT);
        
        // Base url
        String url = WebServiceConstants.COMMENT.URL;

        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.ID_USER, idUser));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.CODE, code));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.COMMENT, commentToBase64));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.NOTE, note));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.TAG, theMagicSentence));
        
        DefaultHttpClient httpClient = new DefaultHttpClient();

        try
        {
        	HttpPost httpPost = new HttpPost(url);
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
	
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        
	        String response = EntityUtils.toString(httpEntity);
	        
	        JSONObject jsonObject = new JSONObject(response);
	        
	        result = jsonObject.getString(WebServiceConstants.COMMENT.SUCCESS).toString();
		    
		    return result;
		    	
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