/*
 * Ask DB for retrieving list of uv from a specific category (CS, TM, Autres)
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.utt.topuv.constant.WebServiceConstants;
import fr.utt.topuv.model.Uv;

public class GetListUvService extends AsyncTask<String, String, ArrayList<Uv>>
{
	/*
	Activity motherActivity;
	// Progress Dialog
    private ProgressDialog pDialog;
	
	public GetListUvService(Activity activity) {
		motherActivity = activity;
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(motherActivity);
        pDialog.setMessage("Chargement des UVs en cours...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
	
	protected void onPostExecute(String file_url) {
        // dismiss the dialog once product deleted
        pDialog.dismiss();
    }
	*/
	@Override
    protected ArrayList<Uv> doInBackground(String... params)
    {
		String category = params[0];

        // Base uri
        String uri = WebServiceConstants.UVS.URL;

        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.UVS.CATEGORIE, category)); 
        
        DefaultHttpClient httpClient = new DefaultHttpClient();

        ArrayList<Uv> ArrayListUv = new ArrayList<Uv>();
        try
        {
        	HttpPost httpPost = new HttpPost(uri);
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        
	        String response = EntityUtils.toString(httpEntity);
	        
	        JSONObject jsonObject = new JSONObject(response);
	        
            if(jsonObject.has(WebServiceConstants.UVS.UVS))
            {
                JSONArray jsonArray = jsonObject.getJSONArray(WebServiceConstants.UVS.UVS);
                for(int index = 0; index < jsonArray.length(); index++)
                {
                    Uv uvSelected = new Uv();
                    uvSelected.code = jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.CODE);
                    uvSelected.designation = jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.DESIGNATION);

                    ArrayListUv.add(uvSelected);
                }
            }
            
            return ArrayListUv;
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