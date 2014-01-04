/*
 * Ask DB to add comment and rate on specific UV
 * 
 * 
 */

package fr.utt.topuv.service;

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

public class PutCommentService extends AsyncTask<String, Void, String>
{
	@Override
    protected String doInBackground(String... params)
    {
		String idUser = params[0];
		String code = params[1];       
        String comment = params[2];
        String note = params[3];

        // Base url
        String url = WebServiceConstants.COMMENT.URL;

        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.ID_USER, idUser));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.CODE, code));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.COMMENT, comment));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.NOTE, note));

        DefaultHttpClient httpClient = new DefaultHttpClient();

        try
        {
        	HttpPost httpPost = new HttpPost(url);
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