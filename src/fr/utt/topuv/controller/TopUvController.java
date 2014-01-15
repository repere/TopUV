package fr.utt.topuv.controller;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import fr.utt.topuv.R;
import fr.utt.topuv.activity.UvActivity;
import fr.utt.topuv.adapter.ListUvAdapter;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.sqlite.UvDb;

public class TopUvController extends Fragment implements OnClickListener, OnItemClickListener
{	
	private ArrayList<Uv> arrayListUvs = new ArrayList<Uv>();
	ListUvAdapter adapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_topuv, null);
        viewGroup.findViewById(R.id.topuv_button_flop).setOnClickListener(this);
        viewGroup.findViewById(R.id.topuv_button_top).setOnClickListener(this);
        
        UvDb uvDb= new UvDb(getActivity().getApplicationContext());
        uvDb.read();
        
        arrayListUvs = uvDb.getAllUvByOrder("DESC");
        
        adapter = new ListUvAdapter(this.getActivity().getApplicationContext(),R.layout.uvs_list_entry, arrayListUvs);

        ListView listView = (ListView) viewGroup.findViewById(R.id.topuv_listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        
        uvDb.close();
        
        return viewGroup;
    }
	
	@Override
    public void onClick(View v)
    {
		UvDb uvDb= new UvDb(getActivity().getApplicationContext());
        uvDb.read();
        arrayListUvs.clear();
        adapter.clear();
		
		switch(v.getId())
		{
			case R.id.topuv_button_top:
				arrayListUvs = uvDb.getAllUvByOrder("DESC");
				break;
				
			case R.id.topuv_button_flop:
				arrayListUvs = uvDb.getAllUvByOrder("ASC");
				break;
		}
		
		adapter.addAll(arrayListUvs);

        adapter.notifyDataSetChanged();
        
		uvDb.close();
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	{
		Uv uvSelected = (Uv) parent.getAdapter().getItem(position);
		
		Intent intent = new Intent(this.getActivity(), UvActivity.class);
        intent.putExtra(IntentConstants.CODE, uvSelected.getCode());
        
        this.startActivity(intent);	
	}
}