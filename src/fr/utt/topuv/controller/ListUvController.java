/*
 * Display list of UV link to the category wanted (CS, TM, Autres)
 * Launch UvActivity when clicking on specific UV
 * 
 */

package fr.utt.topuv.controller;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.utt.topuv.R;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.service.GetListUvService;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListUvController extends ListFragment implements OnItemClickListener
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = (View) inflater.inflate(R.layout.fragment_list, container, false);
		return view;
	}
	

	public void getListUv(String category)
	{
		
		try
        {
            GetListUvService listUvService = new GetListUvService(this.getActivity());
            ArrayList<Uv> messages = listUvService.execute(category).get();

            this.setListAdapter(new ListUvAdapter(this.getActivity(), messages));
        }
        catch(InterruptedException interruptedException)
        {

        }
        catch(ExecutionException executionException)
        {

        }
		
		/* Precedent method
		//Sample : Get data list from the Data Access Objects in string.xml
		String[] items = this.getActivity().getResources().getStringArray(R.array.contacts_list);

		//Populate the list with data
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), R.layout.fragment_simple_row, items);
        ((ListView) this.getView().findViewById(R.id.list)).setAdapter(adapter);
		
		//Set the event listener
		((ListView) this.getView().findViewById(R.id.list)).setOnItemClickListener(this);
		*/
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		//launch new activity : uv review + list of comment + add comment		
		
	}
	
	private class ListUvAdapter extends ArrayAdapter<Uv>
    {
        public ListUvAdapter(Context context, ArrayList<Uv> uvs)
        {
            super(context, R.layout.uvs_list_entry, uvs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            int layout = R.layout.uvs_list_entry;

            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this.getContext()).inflate(layout, null);

            ((TextView) viewGroup.findViewById(R.id.codeUvFromDB)).setText(this.getItem(position).code);
            ((TextView) viewGroup.findViewById(R.id.designationUvFromDB)).setText(this.getItem(position).designation);

            return viewGroup;
        }
    }
}
