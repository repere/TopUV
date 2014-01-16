/*
 * Control when user clic on button to send his comment and mark 
 */ 

package fr.utt.topuv.controller;

import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.User;
import fr.utt.topuv.service.CustomApplication;
import fr.utt.topuv.service.PutCommentService;
import fr.utt.topuv.R;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class UvCommentController extends Fragment implements OnClickListener
{
	private String code;
	private int idUser;
	private String note;
	private String idUserToString;
	private String comment;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_uv_comment, null);
        
        code = this.getActivity().getIntent().getStringExtra(IntentConstants.CODE);
        ((TextView) viewGroup.findViewById(R.id.uv_code)).setText(" "+ code + " :");
        
        viewGroup.findViewById(R.id.send_comment_button).setOnClickListener(this);
                
        return viewGroup;
    }
	
    @Override
    public void onClick(View v)
    {
        code = this.getActivity().getIntent().getStringExtra(IntentConstants.CODE);
		idUser = User.getId();
		
		//Convert the idUser int to String to due AsyncTask restriction (only accept one type, here String)
		idUserToString = String.valueOf(idUser);
    	
    	RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup1);
    	int selectedId = radioGroup.getCheckedRadioButtonId();
    	RadioButton radioButtonSelected = (RadioButton) getActivity().findViewById(selectedId);
    	note = (String) radioButtonSelected.getText();
    	note = note.substring(0, 1);
    	
        comment = ((EditText) this.getView().findViewById(R.id.comment_to_add)).getText().toString();
        
        // Basic local validation
        boolean error = false;
        if(comment.length() == 0)
        {
            ((EditText) this.getView().findViewById(R.id.comment_to_add)).setError(this.getString(R.string.comment_required));
            error = true;
        }
        
        else if(comment.length() < 2 & comment.length() > 0 )
        {
            ((EditText) this.getView().findViewById(R.id.comment_to_add)).setError(this.getString(R.string.comment_length));
            error = true;
        }
        
        if(error)
        {
            return;
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_message);
        builder.setIcon(R.drawable.ic_action_warning);
        builder.setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() 
               {
                   public void onClick(DialogInterface dialog, int id) 
                   {
                	   PutCommentService putCommentService = new PutCommentService(getActivity());
                       putCommentService.execute(idUserToString, code, comment, note);
                   }
               });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() 
               {
                   public void onClick(DialogInterface dialog, int id) 
                   {
                	   dialog.dismiss();
                   }
               });

        builder.show();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) 
    {
    	super.onSaveInstanceState(outState);
    	
    	((CustomApplication) getActivity().getApplication()).detach(getActivity());
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) 
    {
    	super.onActivityCreated(savedInstanceState);
    	
    	((CustomApplication) getActivity().getApplication()).attach(getActivity());
    }
}
