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

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import fr.utt.topuv.constant.WebServiceConstants;
import fr.utt.topuv.model.Comment;

public class GetListCommentService extends AsyncTask<String, String, ArrayList<Comment>>
{
	Activity motherActivity;
	// Progress Dialog
    private ProgressDialog pDialog;
	
	public GetListCommentService(Activity activity) {
		motherActivity = activity;
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(motherActivity);
        pDialog.setMessage("Chargement des notes et commentaires en cours...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
	
	protected void onPostExecute(String file_url) {
        // dismiss the dialog once product deleted
        pDialog.dismiss();
    }
	
	@Override
    protected ArrayList<Comment> doInBackground(String... params)
    {
		String uvCode = params[0];

        // Base uri
        String uri = WebServiceConstants.COMMENTS.URL;

        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENTS.CODE, uvCode)); 
        
        DefaultHttpClient httpClient = new DefaultHttpClient();

        ArrayList<Comment> ArrayListComments = new ArrayList<Comment>();
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
                    Comment commentSelected = new Comment();
                    commentSelected.lastname = jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.LASTNAME);
                    commentSelected.surname = jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.SURNAME);
                    commentSelected.note = (int) jsonArray.getJSONObject(index).getInt(WebServiceConstants.COMMENTS.MARK);
                    commentSelected.comment = jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.COMMENT);
                    commentSelected.date = jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.DATE);

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
