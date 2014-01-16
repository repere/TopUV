package fr.utt.topuv.service;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import fr.utt.topuv.R;
import fr.utt.topuv.activity.MenuActivity;
import fr.utt.topuv.constant.WebServiceConstants;
import fr.utt.topuv.model.Note;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.sqlite.CommentDb;
import fr.utt.topuv.sqlite.UvDb;

public class GetAllService extends CustomAsyncTask<String, Integer, Integer>
{
	private ProgressDialog pDialog;
	private int mCurrProgress;
	
	//To calculate how many comments/uvs we have to dl
	private int numberOfUvDownloaded;
	private int numberOfNoteDownloaded;
	
	//To change title and message during the second DL
	private int secondTask = 1;
	
	// THE MAGIC SENTENCE :p
	private String theMagicSentence = "sesameOuvreToi!";
	
	public GetAllService(Activity activity) 
	{
		super(activity);
	}
	
	@Override
	protected void onPreExecute() 
	{
		super.onPreExecute();
		showProgressDialog();
	}
	
	@Override
	protected void onActivityDetached() 
	{
		if (pDialog != null) 
		{
			pDialog.dismiss();
			pDialog = null;
		}
	}
	
	@Override
	protected void onActivityAttached() 
	{
		showProgressDialog();
	}
	
	private void showProgressDialog() 
	{
		pDialog = new ProgressDialog(mActivity);
        pDialog.setTitle("Récupération des UVs 1/2");
        pDialog.setMessage("Chargement en cours...");
        pDialog.setIcon(R.drawable.ic_action_import_export);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(false);
        pDialog.show();
	}
	
	// This is called each time we call publishProgress()
	@Override
	protected void onProgressUpdate(Integer... progress) 
	{
		mCurrProgress = progress[0];
		if (mActivity != null) 
		{
			pDialog.setProgress(mCurrProgress);

	    	int i = secondTask;
	    	if (i == 2)
	    	{
	    		pDialog.setTitle("Récupération des Notes 2/2");
	    		pDialog.setMessage("Encore un peu de patience !");
	    	}
			
		}
	}
	
	@Override
	protected void onPostExecute(Integer numberOfUvDownloaded) 
	{
		super.onPostExecute(numberOfUvDownloaded);

		if (mActivity != null) 
		{
			pDialog.dismiss();
			this.restartActivity();
		}
	}
	
	@Override
    protected Integer doInBackground(String... urls)
    {	
		String url_uv = urls[0];
		String url_comment = urls[1];
		
		UvDb uvDb = new UvDb(mActivity);
		CommentDb commentDb = new CommentDb(mActivity);
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.UVS.TAG, theMagicSentence));
        
        MakeHttpPostRequest makeHttopPostRequest = new MakeHttpPostRequest();
        
        try
        {  
        	uvDb.open();

        	JSONObject jsonObject = makeHttopPostRequest.execute(url_uv, nameValuePairs);
	        
	        // UV part
            if(jsonObject.has(WebServiceConstants.UVS.UVS))
            {          	
            	JSONArray jsonArray = jsonObject.getJSONArray(WebServiceConstants.UVS.UVS);
            	
            	numberOfUvDownloaded = jsonArray.length() - 1;
            	
                for(int index = 0; index < jsonArray.length(); index++)
                {
                    Uv uvSelected = new Uv();
                    uvSelected.setId(jsonArray.getJSONObject(index).getInt(WebServiceConstants.UVS.ID));
                    uvSelected.setCode(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.CODE));
                    uvSelected.setDesignation(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.DESIGNATION));
                    uvSelected.setCredit(jsonArray.getJSONObject(index).getInt(WebServiceConstants.UVS.CREDIT));
                    uvSelected.setDescription(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.DESCRIPTION));
                    uvSelected.setNote(Float.valueOf(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.NOTE)));
                    uvSelected.setCategorie(jsonArray.getJSONObject(index).getString(WebServiceConstants.UVS.CATEGORIE));
                    
                    if(uvDb.isUvExist(uvSelected.getId()))
                    {
                    	uvDb.updateUv(uvSelected.getId(), uvSelected);
                    }
                    else
                    {
                    	uvDb.insertUv(uvSelected);
                    }
                    
                    //Update progress bar
                    publishProgress((int) ((index / (float) numberOfUvDownloaded) * 100));
                }
            }
            
            
            //Again but for Comment
            commentDb.open();
            jsonObject = makeHttopPostRequest.execute(url_comment, nameValuePairs);
            
            // Comments part
            if(jsonObject.has(WebServiceConstants.COMMENTS.COMMENTS))
            {           	
            	secondTask = 2;
            	
            	JSONArray jsonArray = jsonObject.getJSONArray(WebServiceConstants.COMMENTS.COMMENTS);
            	
            	numberOfNoteDownloaded = jsonArray.length() - 1;
            	
                for(int index = 0; index < jsonArray.length(); index++)
                {
                    Note noteSelected = new Note();
                    noteSelected.setFirstName(jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.FIRSTNAME));
                	noteSelected.setLastName(jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.LASTNAME));
                	noteSelected.setId(jsonArray.getJSONObject(index).getInt(WebServiceConstants.COMMENTS.ID));
                	noteSelected.setIdUser(jsonArray.getJSONObject(index).getInt(WebServiceConstants.COMMENTS.ID_USER));
                	noteSelected.setIdUv(jsonArray.getJSONObject(index).getInt(WebServiceConstants.COMMENTS.ID_UV));
                	noteSelected.setComment(jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.COMMENT));
                	noteSelected.setNote(jsonArray.getJSONObject(index).getInt(WebServiceConstants.COMMENTS.NOTE));
                	noteSelected.setDate(jsonArray.getJSONObject(index).getString(WebServiceConstants.COMMENTS.DATE));
 
                	if(commentDb.isCommentExist(noteSelected.getId()))
                    {
                		commentDb.updateComment(noteSelected.getId(), noteSelected);
                    }
                    else
                    {
                    	commentDb.insertComment(noteSelected);
                    }
                    
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
		Intent intent = new Intent(mActivity, MenuActivity.class);
        
		mActivity.startActivity(intent);
	}
}
