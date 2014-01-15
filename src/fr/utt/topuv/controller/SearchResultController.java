package fr.utt.topuv.controller;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import fr.utt.topuv.R;
import fr.utt.topuv.activity.UvActivity;
import fr.utt.topuv.adapter.ListUvAdapter;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.sqlite.UvDb;

public class SearchResultController extends Fragment implements OnItemClickListener
{
	private ArrayList<Uv> uvs = new ArrayList<Uv>();
	private String designation;
	private String description;
	private String category;
	
	private ListView listViewUvResult;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.list_uvs_fragment_search, null);
    	
    	listViewUvResult = (ListView) viewGroup.findViewById(R.id.listViewUvResult);
    	listViewUvResult.setEmptyView(viewGroup.findViewById(R.id.listViewUvResultEmpty));
    	listViewUvResult.setOnItemClickListener(this);
    	
    	UvDb uvDb= new UvDb(getActivity().getApplicationContext());
        uvDb.read();
        
        designation = this.getActivity().getIntent().getStringExtra(IntentConstants.DESIGNATION);
        description = this.getActivity().getIntent().getStringExtra(IntentConstants.DESCRIPTION);
        category = this.getActivity().getIntent().getStringExtra(IntentConstants.CATEGORIE);
        
		
        ArrayList<Uv> ArrayListUvs = uvDb.searchUv(category, designation, description);
        
        uvs = ArrayListUvs;
        
        ListUvAdapter adapter = new ListUvAdapter(this.getActivity(),R.layout.uvs_list_entry, uvs);
        
        listViewUvResult.setAdapter(adapter);
        
        uvDb.close();
    	
    	return viewGroup;
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	{
		Uv uvSelected = (Uv) parent.getItemAtPosition(position);

        Intent intent = new Intent(this.getActivity(), UvActivity.class);
        intent.putExtra(IntentConstants.CODE, uvSelected.getCode());
        
        this.startActivity(intent);
		
	}
}
