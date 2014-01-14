package fr.utt.topuv.controller;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import fr.utt.topuv.R;
import fr.utt.topuv.activity.UvActivity;
import fr.utt.topuv.adapter.ListUvAdapter;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.sqlite.UvDb;

public class SearchResultController extends ListFragment
{
	private ArrayList<Uv> uvs = new ArrayList<Uv>();
	private String designation;
	private String description;
	private String category;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.list_uvs_fragment_search, null);
    	
    	UvDb uvDb= new UvDb(getActivity().getApplicationContext());
        uvDb.read();
        
        designation = this.getActivity().getIntent().getStringExtra(IntentConstants.DESIGNATION);
        description = this.getActivity().getIntent().getStringExtra(IntentConstants.DESCRIPTION);
        category = this.getActivity().getIntent().getStringExtra(IntentConstants.CATEGORIE);
        
		
        ArrayList<Uv> ArrayListUvs = uvDb.searchUv(category, designation, description);
        
        uvs = ArrayListUvs;

        ListUvAdapter adapter = new ListUvAdapter(this.getActivity().getApplicationContext(),R.layout.uvs_list_entry, uvs);
        
        this.setListAdapter(adapter);
        
        uvDb.close();
    	
    	return viewGroup;
    }
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Uv uvSelected = (Uv) this.getListAdapter().getItem(position);

        Intent intent = new Intent(this.getActivity(), UvActivity.class);
        intent.putExtra(IntentConstants.CODE, uvSelected.getCode());
        
        int idUser = this.getActivity().getIntent().getIntExtra(IntentConstants.ID_USER, 0);
        intent.putExtra(IntentConstants.ID_USER, idUser);
        
        this.startActivity(intent);
    }
    
}
