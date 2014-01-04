/*
 * Ask DB for retrieving list of uv from a specific category (CS, TM, Autres)
 * 
 * 
 */


package fr.utt.topuv.service;

import android.app.Activity;
//import android.app.ProgressDialog;
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

public class GetListUvService extends AsyncTask<String, Void, ArrayList<Uv>>
{
	Activity motherActivity;

	public GetListUvService(Activity activity) {
		motherActivity = activity;
	}
	
	
	/*
	// Progress Dialog
    private ProgressDialog pDialog;
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(motherActivity);
        pDialog.setTitle("Récupération des UVs");
        pDialog.setMessage("Chargement en cours...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
    }
	
	protected void onPostExecute(ArrayList<Uv> resultOfAsyncTask) {
        // dismiss the dialog once product deleted
        pDialog.dismiss();
    }*/
	
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
                    uvSelected.setCode(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.CODE));
                    uvSelected.setDesignation(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.DESIGNATION));
                    uvSelected.setCredit(jsonArray.getJSONObject(index).getInt(WebServiceConstants.UVS.CREDIT));
                    uvSelected.setDescription(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.DESCRIPTION));
                    uvSelected.setNote(jsonArray.getJSONObject(index).getInt(WebServiceConstants.UVS.NOTE));

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