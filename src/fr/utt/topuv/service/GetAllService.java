package fr.utt.topuv.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import fr.utt.topuv.activity.MenuActivity;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.constant.WebServiceConstants;
import fr.utt.topuv.model.Note;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.sqlite.CommentDb;
import fr.utt.topuv.sqlite.UvDb;

public class GetAllService extends AsyncTask<String, Integer, Integer>
{
	private Activity motherActivity;
	
	private int numberOfUvDownloaded;
	private int numberOfNoteDownloaded;
	
	private int secondTask = 1;
	
	// Progress Dialog
    private ProgressDialog pDialog = null;   

	public GetAllService(Activity activity) 
	{
		motherActivity = activity;
	}

	@Override
    protected void onPreExecute() 
	{
        super.onPreExecute();
        pDialog = new ProgressDialog(motherActivity);
        pDialog.setTitle("Récupération des UVs 1/2");
        pDialog.setMessage("Chargement en cours...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(false);
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
    	int i = secondTask;
    	if (i == 2)
    	{
    		pDialog.setTitle("Récupération des Notes 2/2");
    	}
    }
	
	@Override
    protected Integer doInBackground(String... urls)
    {	
		String url_uv = urls[0];
		String url_comment = urls[1];
		
		UvDb uvDb = new UvDb(motherActivity);
		CommentDb commentDb = new CommentDb(motherActivity);

        try
        {  
        	uvDb.open();
        	JSONObject jsonObject = makeHttpPostRequestAndReturnJsonObject(url_uv);
	        
	        // UV part
            if(jsonObject.has(WebServiceConstants.UVS.UVS))
            {
            	//Delete all the Uv Sqlite Db and rebuilt it
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
            
            
            //Again but for Comment
            commentDb.open();
            jsonObject = makeHttpPostRequestAndReturnJsonObject(url_comment);
            
            // Comments part
            if(jsonObject.has(WebServiceConstants.COMMENTS.COMMENTS))
            {           	
            	secondTask = 2;

            	//Delete all the Comment Sqlite Db and rebuilt it
            	commentDb.onUpgrade();
            	
            	JSONArray jsonArray = jsonObject.getJSONArray(WebServiceConstants.COMMENTS.COMMENTS);
            	
            	numberOfNoteDownloaded = jsonArray.length() - 1;
            	
                for(int index = 0; index < jsonArray.length(); index++)
                {
                    Note noteSelected = new Note();
                    noteSelected.setFirstName(jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.FIRSTNAME));
                	noteSelected.setLastName(jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.LASTNAME));
                    noteSelected.setIdUser(jsonArray.getJSONObject(index).getInt(WebServiceConstants.COMMENTS.ID_USER));
                	noteSelected.setIdUv(jsonArray.getJSONObject(index).getInt(WebServiceConstants.COMMENTS.ID_UV));
                	noteSelected.setComment(jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.COMMENT));
                	noteSelected.setNote(jsonArray.getJSONObject(index).getInt(WebServiceConstants.COMMENTS.NOTE));
                	noteSelected.setDate(jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.DATE));
 
                    commentDb.insertComment(noteSelected);
                    
                    //Update progress bar
                    publishProgress((int) ((index / (float) numberOfNoteDownloaded) * 100));
                }
            }
            
            return 1;
        }
        
        catch(JSONException jsonException)
        {

        }
        
        finally
        {
        	uvDb.close();
        	commentDb.close();
        }
	        
		return 0;
    }
	
	private void restartActivity()
	{    
        motherActivity.finish();
		Intent intent = new Intent(motherActivity, MenuActivity.class);
		
		int idUser = motherActivity.getIntent().getIntExtra(IntentConstants.ID_USER,0);
        intent.putExtra(IntentConstants.ID_USER, idUser);
        
        motherActivity.startActivity(intent);
	}
	
	private JSONObject makeHttpPostRequestAndReturnJsonObject(String url)
	{
		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
	    	
	    	HttpPost httpPost = new HttpPost(url);
	
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        
	        String response = EntityUtils.toString(httpEntity);
	        
	        JSONObject jsonObject = new JSONObject(response);
	        
	        return jsonObject;
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
