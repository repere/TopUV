/*
 * Ask DB for retrieving list of uv from a specific category (CS, TM, Autres)
 * 
 * 
 */


package fr.utt.topuv.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
//import android.app.ProgressDialog;
import android.os.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.utt.topuv.activity.MenuActivity;
import fr.utt.topuv.constant.WebServiceConstants;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.sqlite.UvsDb;

import java.io.IOException;

public class GetAllUvService extends AsyncTask<Void, Integer, Integer>
{
	Activity motherActivity;
	
	private int numberOfUvDownloaded;

	public GetAllUvService(Activity activity) {
		motherActivity = activity;
	}
	
	// Progress Dialog
    private ProgressDialog pDialog = null;
	
	@Override
    protected void onPreExecute() 
	{
        super.onPreExecute();
        pDialog = new ProgressDialog(motherActivity);
        pDialog.setTitle("Récupération des UVs");
        pDialog.setMessage("Chargement en cours...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
        pDialog.show();
    }
	
	@Override
	protected void onPostExecute(Integer numberOfUvDownloaded) 
	{      
		// dismiss the dialog once product deleted
        pDialog.dismiss();
        
        this.restartActivity();
    }
	
	// This is called each time you call publishProgress()
    protected void onProgressUpdate(Integer... progress) 
    {
        super.onProgressUpdate(progress);
    	pDialog.setProgress(progress[0]);
    }
	
	@Override
    protected Integer doInBackground(Void...params)
    {
        // Base uri
        String uri = WebServiceConstants.UVS.URL;
        
        DefaultHttpClient httpClient = new DefaultHttpClient();
        
        UvsDb uvDb= new UvsDb(motherActivity);
        uvDb.open();
        
        try
        {
        	HttpPost httpPost = new HttpPost(uri);
	
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        
	        String response = EntityUtils.toString(httpEntity);
	        
	        JSONObject jsonObject = new JSONObject(response);
	        
            if(jsonObject.has(WebServiceConstants.UVS.UVS))
            {
                //Delete all the Sqlite Db and rebuilt it
            	uvDb.onUpgrade();
            	
            	JSONArray jsonArray = jsonObject.getJSONArray(WebServiceConstants.UVS.UVS);
            	
            	numberOfUvDownloaded = jsonArray.length() - 1;
            	
                for(int index = 0; index < jsonArray.length(); index++)
                {
                    Uv uvSelected = new Uv();
                    uvSelected.setCode(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.CODE));
                    uvSelected.setDesignation(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.DESIGNATION));
                    uvSelected.setCredit(jsonArray.getJSONObject(index).getInt(WebServiceConstants.UVS.CREDIT));
                    uvSelected.setDescription(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.DESCRIPTION));
                    uvSelected.setNote(Float.valueOf(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.NOTE)));
                    uvSelected.setCategorie(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.CATEGORIE));
                    
                    uvDb.insertUv(uvSelected);
                    
                    //Update progress bar
                    publishProgress((int) ((index / (float) numberOfUvDownloaded) * 100));
                }
            }
            
            return numberOfUvDownloaded;
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
        
        finally
        {
        	uvDb.close();
        }
		return 0;
    }
	
	private void restartActivity()
	{    
        motherActivity.finish();
		Intent intent = new Intent(motherActivity, MenuActivity.class);
        motherActivity.startActivity(intent);
	}
}