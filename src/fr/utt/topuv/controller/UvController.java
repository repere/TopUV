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
import android.widget.RatingBar;
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
    private String code_designation;
    private int credit;
    private String description;
    private int note;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_uv, null);
 
    	//Retrieve code, designation, note and credit from intent
    	code = this.getActivity().getIntent().getStringExtra(IntentConstants.CODE);
        designation = this.getActivity().getIntent().getStringExtra(IntentConstants.DESIGNATION);
        code_designation = code + " : " + designation;
        note = this.getActivity().getIntent().getIntExtra(IntentConstants.NOTE,0);
        credit = this.getActivity().getIntent().getIntExtra(IntentConstants.CREDIT,0);
    	
    	((TextView) viewGroup.findViewById(R.id.name_uv_bar)).setText(code_designation);
        
        ((TextView) viewGroup.findViewById(R.id.uv_description)).setText(description);
        
        ((TextView) viewGroup.findViewById(R.id.credit_uv)).setText(credit);
        
        ((RatingBar) viewGroup.findViewById(R.id.ratingBar_current_note)).setRating(note);
        
        viewGroup.findViewById(R.id.add_comment_button).setOnClickListener(this);

        return viewGroup;
    }

    @Override
    public void onClick(View v)
    {
    	Intent intent = new Intent(this.getActivity(), UvCommentActivity.class);
    	intent.putExtra(IntentConstants.CODE, code);
        this.startActivity(intent);
    }
}