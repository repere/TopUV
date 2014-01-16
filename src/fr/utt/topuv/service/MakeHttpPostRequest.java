package fr.utt.topuv.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class MakeHttpPostRequest 
{
	public MakeHttpPostRequest() 
	{
		super();
	}
	
	public JSONObject execute(String inUrl, ArrayList<NameValuePair> inNameValuePairs)
	{
		try
		{ 
			DefaultHttpClient httpClient = new DefaultHttpClient();
	    	
	    	HttpPost httpPost = new HttpPost(inUrl);
	    	httpPost.setEntity(new UrlEncodedFormEntity(inNameValuePairs));
	
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
