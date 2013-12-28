/*
 * Display list of UV link to the category wanted (CS, TM, Autres)
 * Launch UvActivity when clicking on specific UV
 * 
 */

package fr.utt.topuv.controller;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.utt.topuv.R;
import fr.utt.topuv.activity.UvActivity;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.service.GetListUvService;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListUvController extends ListFragment
{	
	
	public static final String ARG_SECTION_TYPE = "section_type";
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        try
        {
        	GetListUvService getListUvService = new GetListUvService(this.getActivity());
            ArrayList<Uv> ArrayListUvs = getListUvService.execute(ARG_SECTION_TYPE).get();

            this.setListAdapter(new ListUvAdapter(this.getActivity(), ArrayListUvs));
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
        intent.putExtra(IntentConstants.CODE, uvSelected.code);

        this.startActivity(intent);
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
