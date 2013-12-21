/*
 * Display list of comments link to the specific UV
 * Display additional informations when clicking on comments from other users
 * 
 */

package fr.utt.topuv.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import fr.utt.topuv.R;

public class ListCommentController extends Fragment implements OnItemClickListener
{
	
	public static final String CS = "cs";
	public static final String TM = "tm";
	public static final String AUTRES = "autres";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Create new fragment_list_comment that show properly comment + rating
		View view = inflater.inflate(R.layout.fragment_list, null);
		return view;
	}
	

	public void getListComment(String typeOfUv)
	{
		//Get list of comments and rates selected from Mysql db
		//Use ListCommentsService
		
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
		//Show extra toast that indicates post date maybe ?		
		
	}
}
