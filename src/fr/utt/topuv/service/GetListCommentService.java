/*
 * Ask DB for retrieving list of comments and rates from a specific UV
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import fr.utt.topuv.constant.WebServiceConstants;
import fr.utt.topuv.model.Note;

public class GetListCommentService extends AsyncTask<String, Void, ArrayList<Note>>
{	
	@Override
    protected ArrayList<Note> doInBackground(String... params)
    {
		String uvCode = params[0];

        // Base uri
        String uri = WebServiceConstants.COMMENTS.URL;

        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENTS.CODE, uvCode)); 
        
        DefaultHttpClient httpClient = new DefaultHttpClient();

        ArrayList<Note> ArrayListComments = new ArrayList<Note>();
        try
        {
        	HttpPost httpPost = new HttpPost(uri);
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        
	        String response = EntityUtils.toString(httpEntity);
	        
	        JSONObject jsonObject = new JSONObject(response);
	        
            if(jsonObject.has(WebServiceConstants.COMMENTS.COMMENTS))
            {
                JSONArray jsonArray = jsonObject.getJSONArray(WebServiceConstants.COMMENTS.COMMENTS);
                for(int index = 0; index < jsonArray.length(); index++)
                {
                    Note commentSelected = new Note();
                    commentSelected.setNote(jsonArray.getJSONObject(index).getInt(WebServiceConstants.COMMENTS.NOTE));
                    commentSelected.setComment(jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.COMMENT));
                    commentSelected.setDate(jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.DATE));
                    commentSelected.setFirstName(jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.FIRSTNAME));
                    commentSelected.setLastName(jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.LASTNAME));

                    ArrayListComments.add(commentSelected);
                }
            }
            
            return ArrayListComments;
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
