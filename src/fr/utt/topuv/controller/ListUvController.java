/*
 * Populate listview of Uv in the view pager
 */

package fr.utt.topuv.controller;

import java.util.ArrayList;
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
import fr.utt.topuv.sqlite.UvDb;

public class ListUvController extends ListFragment
{
	private ArrayList<Uv> uvs = new ArrayList<Uv>();
	Bundle bundle;
	String actualCategoryOfUv;
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        
        UvDb uvDb= new UvDb(getActivity().getApplicationContext());
        uvDb.read();
        
        bundle = getArguments();
		actualCategoryOfUv = bundle.getString(IntentConstants.CODE);
		
        ArrayList<Uv> ArrayListUvs = uvDb.getUvByCategory(actualCategoryOfUv);
        
        uvs = ArrayListUvs;

        ListUvAdapter adapter = new ListUvAdapter(this.getActivity().getApplicationContext(),R.layout.uvs_list_entry, uvs);
        
        this.setListAdapter(adapter);
        
        uvDb.close();

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
        intent.putExtra(IntentConstants.CATEGORIE, uvSelected.getCategorie());
        
        int idUser = this.getActivity().getIntent().getIntExtra(IntentConstants.ID_USER,0);
        intent.putExtra(IntentConstants.ID_USER, idUser);
        
        this.startActivity(intent);
    }
    
}
