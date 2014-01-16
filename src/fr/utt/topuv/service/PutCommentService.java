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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import fr.utt.topuv.R;
import fr.utt.topuv.constant.WebServiceConstants;
import fr.utt.topuv.model.Note;
import fr.utt.topuv.model.User;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.sqlite.CommentDb;
import fr.utt.topuv.sqlite.UvDb;

public class PutCommentService extends AsyncTask<String, Void, String>
{
	//To create the Progress Dialog
	private Activity motherActivity;
	
	// THE MAGIC SENTENCE :p
	private String theMagicSentence = "sesameOuvreToi!";
	
	private String result;
	private String resultToGive;
	
	// Note info
	private String idUser;
	private String code;       
	private String comment;
	private String note;
	
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
        pDialog.setIcon(R.drawable.ic_action_send_now);
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
	    	
	    	UvDb uvDb = new UvDb(motherActivity);
			CommentDb commentDb = new CommentDb(motherActivity);
			uvDb.open();
			commentDb.open();
			
			Uv uv = uvDb.getUvByUvCode(code);
			int idOfUv = uv.getId();
			
			Date dateOfToday = new Date();
			String dateToString = new SimpleDateFormat("yyy-MM-dd",Locale.FRANCE).format(dateOfToday);
			
			int noteToInt = Integer.parseInt(note);
			
			ArrayList<Note> arrayListComment;
			arrayListComment = commentDb.getAllComment();
			
			int idNote = arrayListComment.size() + 1;
			
	    	Note noteToInsertInSqliteDb = new Note();
	    	noteToInsertInSqliteDb.setId(idNote);
	    	noteToInsertInSqliteDb.setComment(comment);
	    	noteToInsertInSqliteDb.setDate(dateToString);
	    	noteToInsertInSqliteDb.setFirstName(User.getFirstName());
	    	noteToInsertInSqliteDb.setIdUser(User.getId());
	    	noteToInsertInSqliteDb.setIdUv(idOfUv);
	    	noteToInsertInSqliteDb.setLastName(User.getLastName());
	    	noteToInsertInSqliteDb.setNote(noteToInt);
	    	
	    	commentDb.insertComment(noteToInsertInSqliteDb);
	    	
	    	uvDb.close();
			commentDb.close();
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
		idUser = params[0];
		code = params[1];       
        comment = params[2];
        note = params[3];

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
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.ID_USER, idUser));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.CODE, code));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.COMMENT, commentToBase64));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.NOTE, note));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.COMMENT.TAG, theMagicSentence));
        
        MakeHttpPostRequest makeHttopPostRequest = new MakeHttpPostRequest();
        
        JSONObject jsonObject = makeHttopPostRequest.execute(url, nameValuePairs);
        
        try 
        {
			result = jsonObject.getString(WebServiceConstants.COMMENT.SUCCESS).toString();
			return result;
		} 
        
        catch (JSONException e) 
        {
			e.printStackTrace();
		}
        
        return null;
    }
}