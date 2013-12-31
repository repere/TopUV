package fr.utt.topuv.fragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import fr.utt.topuv.R;
import fr.utt.topuv.activity.UvActivity;
import fr.utt.topuv.adapter.ListUvAdapter;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.service.GetListUvService;

public class ListCsFragment extends ListFragment
{
	private ArrayList<Uv> uvs = new ArrayList<Uv>();
	String actualTypeOfUv = "CS";

	@Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        
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
        intent.putExtra(IntentConstants.CODE, uvSelected.getCode());
        intent.putExtra(IntentConstants.DESIGNATION, uvSelected.getDesignation());
        intent.putExtra(IntentConstants.CREDIT, uvSelected.getCredit());
        intent.putExtra(IntentConstants.DESCRIPTION, uvSelected.getDescription());
        intent.putExtra(IntentConstants.NOTE, uvSelected.getNote());
        intent.putExtra(IntentConstants.CAT, uvSelected.getCat());

        this.startActivity(intent);
    }
    
}
