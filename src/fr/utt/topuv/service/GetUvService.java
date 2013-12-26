/*
 * Ask DB to retrieve informations (description, credit, etc) about specific UV
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
import fr.utt.topuv.model.Uv;

public class GetUvService extends AsyncTask<String, String, Uv>
{
	Activity motherActivity;
	// Progress Dialog
    private ProgressDialog pDialog;
	
	public GetUvService(Activity activity) {
		motherActivity = activity;
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(motherActivity);
        pDialog.setMessage("Chargement de l'UV en cours...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
	
	protected void onPostExecute(String file_url) {
        // dismiss the dialog once product deleted
        pDialog.dismiss();
    }
	
	@Override
    protected Uv doInBackground(String... params)
    {
        String code = params[0];

        // Base uri
        String uri = WebServiceConstants.UV.URI;

        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.UV.CODE, code));

        DefaultHttpClient httpClient = new DefaultHttpClient();

        try
        {
        	HttpPost httpPost = new HttpPost(uri);
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        
	        String response = EntityUtils.toString(httpEntity);
	        
	        JSONObject jsonObject = new JSONObject(response);

            Uv uvSelectedObject = new Uv();
            uvSelectedObject.description = jsonObject.getJSONObject(WebServiceConstants.UV.DESCRIPTION).getString(WebServiceConstants.UV.DESCRIPTION);
            uvSelectedObject.mark = jsonObject.getJSONObject(WebServiceConstants.UV.MARK).getString(WebServiceConstants.UV.MARK);
            uvSelectedObject.credit = jsonObject.getJSONObject(WebServiceConstants.UV.CREDIT).getString(WebServiceConstants.UV.CREDIT);
            
            return uvSelectedObject;
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