/*
 * Add controller on Add comment button
 * Ask UvService to retrieve UV selected
 * Launch UvCommentActivity when button is clicked
 */

package fr.utt.topuv.controller;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.R.string;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.utt.topuv.R;
import fr.utt.topuv.activity.UvCommentActivity;
import fr.utt.topuv.adapter.ListUvAdapter;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.service.GetListUvService;
import fr.utt.topuv.service.GetUvService;

public class UvController extends Fragment implements OnClickListener
{
    private String code;
    private String designation;
    private int credit;
    private String description;
    private int note;
    private String cat;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_uv, null);
 
    	code = this.getActivity().getIntent().getStringExtra(IntentConstants.CODE);
        designation = this.getActivity().getIntent().getStringExtra(IntentConstants.DESIGNATION);
    	
    	((TextView) viewGroup.findViewById(R.id.name_uv_bar)).setText(code);
        
        ((TextView) viewGroup.findViewById(R.id.uv_description)).setText(designation);
        
        //view.findViewById(R.id.send).setOnClickListener(this);

        return viewGroup;
    }

    @Override
    public void onClick(View v)
    {
    	Intent intent = new Intent(this.getActivity(), UvCommentActivity.class);
        this.startActivity(intent);
    }
}