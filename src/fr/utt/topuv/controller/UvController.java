/*
 * Add controller on Add comment button
 * Ask UvService to retrieve UV selected
 * Launch UvCommentActivity when button is clicked
 */

package fr.utt.topuv.controller;

import android.R.string;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import fr.utt.topuv.R;
import fr.utt.topuv.activity.UvCommentActivity;

public class UvController extends Fragment implements OnClickListener
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = (View) inflater.inflate(R.layout.fragment_uv_writer, null);
        view.findViewById(R.id.send).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
    	Intent intent = new Intent(this.getActivity(), UvCommentActivity.class);
        this.startActivity(intent);
    }
    
    public void getUv(string uvSelected)
	{
		//Get UV selected from Mysql db
		//Use UvService
		
	}
}