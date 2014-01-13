package fr.utt.topuv.controller;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import fr.utt.topuv.R;
import fr.utt.topuv.activity.UvActivity;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.sqlite.UvDb;

public class SearchController extends Fragment implements OnClickListener, OnItemClickListener
{	
	private String[] allCodeUv;
	private ListView listview;
	
	private String code;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_search, null);
        viewGroup.findViewById(R.id.search_button_method1).setOnClickListener(this);
        viewGroup.findViewById(R.id.search_button_method2).setOnClickListener(this);
        
        //Populate autocompletetextview
        UvDb uvDb= new UvDb(getActivity().getApplicationContext());
        uvDb.read();
        allCodeUv = uvDb.getAllUv();
        
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, allCodeUv);
        AutoCompleteTextView textView = (AutoCompleteTextView) viewGroup.findViewById(R.id.autoCompleteTextView1);
        textView.setAdapter(autoCompleteAdapter);
        
        
        //Populate category listview
  		String[] items = this.getActivity().getResources().getStringArray(R.array.category_uv_expandablelist);
  		ArrayAdapter<String> uvs_adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, items);
  		listview = (ListView) viewGroup.findViewById(R.id.uvs_ListView);
        listview.setAdapter(uvs_adapter);
        listview.setOnItemClickListener(this);
        
        
        return viewGroup;
    }
	
	@Override
    public void onClick(View v)
    {	
		switch(v.getId())
		{
			case R.id.search_button_method1:
				
				code = ((AutoCompleteTextView) this.getView().findViewById(R.id.autoCompleteTextView1)).getText().toString();
				
				Intent intent = new Intent(this.getActivity(), UvActivity.class);
		        intent.putExtra(IntentConstants.CODE, code);
		        
		        int idUser = this.getActivity().getIntent().getIntExtra(IntentConstants.ID_USER, 0);
		        intent.putExtra(IntentConstants.ID_USER, idUser);
		        
		        this.startActivity(intent);
				break;
				
			case R.id.search_button_method2:
				
				break;
		}
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	{
		//get listview option and set code
	}
}
