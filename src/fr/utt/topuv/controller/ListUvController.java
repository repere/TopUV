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

public class ListUvController extends ListFragment //implements OnItemClickListener
{
	private ArrayList<Uv> uvs = new ArrayList<Uv>();
	public static final String TYPE_OF_ACTUAL_UV = "actual_uv";
	
	
	/*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Bundle args = getArguments();
        String actualTypeOfUv = args.getString(TYPE_OF_ACTUAL_UV);
		
		try
		{
        	GetListUvService getListUvService = new GetListUvService(this.getActivity());
            ArrayList<Uv> ArrayListUvs = getListUvService.execute(actualTypeOfUv).get();
            uvs = ArrayListUvs;
		}
		
		catch(InterruptedException interruptedException)
        {

        }
        
        catch(ExecutionException executionException)
        {

        }
		
		View view = inflater.inflate(R.layout.fragment_list, null);
		myListView = (ListView) view.findViewById(R.id.list);
		
		ListUvAdapter adapter = new ListUvAdapter(container.getContext(),R.layout.uvs_list_entry, uvs);
		
		myListView.setAdapter(adapter);
		
		//Set the event listener
		myListView.setOnItemClickListener(this);
		
		
		return view;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		//Show extra toast that indicates post date maybe ?		
		String tmp = (String) parent.getItemAtPosition(position);
		Intent intent = new Intent(this.getActivity(), UvActivity.class);
        intent.putExtra(IntentConstants.CODE, tmp);

        this.startActivity(intent);
	}
	*/
	

	@Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        String actualTypeOfUv = args.getString(TYPE_OF_ACTUAL_UV);
		
		try
		{
        	GetListUvService getListUvService = new GetListUvService();
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
