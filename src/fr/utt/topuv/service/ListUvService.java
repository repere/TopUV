/*
 * Ask DB for retrieving list of uv from a specific category (CS, TM, Autres)
 * 
 * 
 */


package fr.utt.topuv.service;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.utt.topuv.constant.WebServiceConstants;
import fr.utt.topuv.model.UvSelected;

public class ListUvService extends AsyncTask<String, Void, ArrayList<UvSelected>>
{
    @Override
    protected ArrayList<UvSelected> doInBackground(String... params)
    {
        String token = params[0];
        String contact = params[1];

        // Base uri
        String uri = WebServiceConstants.MESSAGES.URI;

        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.MESSAGES.TOKEN, token));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.MESSAGES.CONTACT, contact));

        uri += "?" + URLEncodedUtils.format(nameValuePairs, "utf-8");

        HttpGet httpGet = new HttpGet(uri);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

        ArrayList<UvSelected> uvSelecteds = new ArrayList<UvSelected>();
        try
        {
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet, new BasicHttpContext());
            String response = EntityUtils.toString(httpResponse.getEntity());

            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.has(WebServiceConstants.MESSAGES.MESSAGES))
            {
                JSONArray jsonArray = jsonObject.getJSONArray(WebServiceConstants.MESSAGES.MESSAGES);
                for(int index = 0; index < jsonArray.length(); index++)
                {
                    UvSelected uvSelected = new UvSelected();
                    uvSelected.message = jsonArray.getJSONObject(index).getString(WebServiceConstants.MESSAGES.MESSAGE);
                    uvSelected.date = jsonArray.getJSONObject(index).getString(WebServiceConstants.MESSAGES.DATE);
                    uvSelected.sent = jsonArray.getJSONObject(index).getBoolean(WebServiceConstants.MESSAGES.SENT);

                    uvSelecteds.add(uvSelected);
                }
            }
            return uvSelecteds;
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
        return uvSelecteds;
    }
}