/*
 * Populate listview of Uv in the view pager
 */

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

public class ListUvController extends Fragment implements OnItemClickListener
{
	private ArrayList<Uv> uvs = new ArrayList<Uv>();
	private Bundle bundle;
	private String actualCategoryOfUv;
	
	private ListView listViewUv;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.list_uvs_fragment, null);
    	listViewUv = (ListView) viewGroup.findViewById(R.id.listViewUv);
    	listViewUv.setEmptyView(viewGroup.findViewById(R.id.listViewUvEmpty));
    	listViewUv.setOnItemClickListener(this);
    	
    	UvDb uvDb= new UvDb(getActivity().getApplicationContext());
        uvDb.read();
        
        bundle = getArguments();
		actualCategoryOfUv = bundle.getString(IntentConstants.CODE);
		
        ArrayList<Uv> ArrayListUvs = uvDb.getUvByCategory(actualCategoryOfUv);
        
        uvs = ArrayListUvs;

        ListUvAdapter adapter = new ListUvAdapter(this.getActivity().getApplicationContext(),R.layout.uvs_list_entry, uvs);
        
        listViewUv.setAdapter(adapter);
        
        uvDb.close();
    	
    	return viewGroup;
    }

	@Override
	public void onItemClick(AdapterView<?>parent, View v, int position, long id) 
	{
		Uv uvSelected = (Uv) parent.getItemAtPosition(position);

        Intent intent = new Intent(this.getActivity(), UvActivity.class);
        intent.putExtra(IntentConstants.CODE, uvSelected.getCode());
        
        int idUser = this.getActivity().getIntent().getIntExtra(IntentConstants.ID_USER, 0);
        intent.putExtra(IntentConstants.ID_USER, idUser);
        
        this.startActivity(intent);
		
	}
    
}
