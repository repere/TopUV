/*
 * Display list of UV link to the category wanted (CS, TM, Autres)
 * Launch UvActivity when clicking on specific UV
 * 
 */

package fr.utt.topuv.controller;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.utt.topuv.R;
import fr.utt.topuv.activity.UvActivity;
import fr.utt.topuv.adapter.ListUvAdapter;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.service.GetListUvService;
import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ListUvController extends ListFragment
{
	private ArrayList<Uv> uvs = new ArrayList<Uv>();
	public static final String TYPE_OF_ACTUAL_UV = "actual_uv";

	@Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        String actualTypeOfUv = args.getString(TYPE_OF_ACTUAL_UV);
        
		try
		{
        	GetListUvService getListUvService = new GetListUvService(this.getActivity());
            ArrayList<Uv> ArrayListUvs = getListUvService.execute(actualTypeOfUv).get();
            uvs = ArrayListUvs;

            ListUvAdapter adapter = new ListUvAdapter(this.getActivity().getApplicationContext(),R.layout.uvs_list_entry, uvs);
            
            this.setListAdapter(adapter);
        }
        catch(InterruptedException interruptedException)
        {

        }
        catch(ExecutionException executionException)
        {

        }
    }
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Uv uvSelected = (Uv) this.getListAdapter().getItem(position);

        Intent intent = new Intent(this.getActivity(), UvActivity.class);
        intent.putExtra(IntentConstants.CODE, uvSelected.code);

        this.startActivity(intent);
    }
    
}
