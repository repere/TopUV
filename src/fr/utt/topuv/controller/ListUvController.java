/*
 * Display list of UV link to the category wanted (CS, TM, Autres)
 * Launch UvActivity when clicking on specific UV
 * 
 */

package fr.utt.topuv.controller;

import fr.utt.topuv.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListUvController extends Fragment implements OnItemClickListener
{
	
	public static final String CS = "cs";
	public static final String TM = "tm";
	public static final String AUTRES = "autres";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = (View) inflater.inflate(R.layout.fragment_list, container, false);
		return view;
	}
	

	public void getListUv()
	{
		//Get list of uv selected from Mysql db
		//Use ListUvService
		
		//Sample : Get data list from the Data Access Objects in string.xml
		String[] items = this.getActivity().getResources().getStringArray(R.array.contacts_list);

		//Populate the list with data
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), R.layout.fragment_simple_row, items);
        ((ListView) this.getView().findViewById(R.id.list)).setAdapter(adapter);
		
		//Set the event listener
		((ListView) this.getView().findViewById(R.id.list)).setOnItemClickListener(this);
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		//launch new activity : uv review + list of comment + add comment		
		
	}
}
