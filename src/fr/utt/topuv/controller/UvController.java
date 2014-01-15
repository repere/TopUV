/*
 * Add controller on Add comment button
 * Launch UvCommentActivity when button is clicked
 */

package fr.utt.topuv.controller;

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
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.sqlite.UvDb;

public class UvController extends Fragment implements OnClickListener
{
    private String code;
    private String designation;
    private String code_designation;
    private int credit;
    private String description;
    private float note;
    
    private Uv currentUv;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_uv, null);
    	
    	UvDb uvDb= new UvDb(getActivity().getApplicationContext());
        uvDb.read();
        
        code = this.getActivity().getIntent().getStringExtra(IntentConstants.CODE);
        
        currentUv = uvDb.getUvByUvCode(code);
        
        designation = currentUv.getDesignation();
        code_designation = code + " : " + designation;
        description = currentUv.getDescription();
        note = currentUv.getNote();
        credit = currentUv.getCredit();
        
        //Set text
    	((TextView) viewGroup.findViewById(R.id.name_uv_bar)).setText(code_designation);
        
        ((TextView) viewGroup.findViewById(R.id.uv_description)).setText(description);
        
        ((TextView) viewGroup.findViewById(R.id.credit_uv)).setText(" " + String.valueOf(credit));
        
        ((RatingBar) viewGroup.findViewById(R.id.ratingBar_current_note)).setRating(note);
        
        //Add listener on button
        viewGroup.findViewById(R.id.add_comment_button).setOnClickListener(this);
        
        return viewGroup;
    }

    @Override
    public void onClick(View v)
    {	
    	Intent intent = new Intent(this.getActivity(), UvCommentActivity.class);
    	
    	code = this.getActivity().getIntent().getStringExtra(IntentConstants.CODE);
    	intent.putExtra(IntentConstants.CODE, code);
        
        this.startActivity(intent);
    }
}