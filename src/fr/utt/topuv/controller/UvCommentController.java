/*
 * Control when user clic on button to send his comment and mark 
 */ 

package fr.utt.topuv.controller;

import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.service.PutCommentService;
import fr.utt.topuv.R;

import android.os.Bundle;
import android.app.Fragment;
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
		idUser = this.getActivity().getIntent().getIntExtra(IntentConstants.ID_USER, 0);
		
		//Convert the idUser int to String to due AsyncTask restriction (only accept one type, here String)
		String idUserToString = String.valueOf(idUser);
    	
    	RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup1);
    	int selectedId = radioGroup.getCheckedRadioButtonId();
    	RadioButton radioButtonSelected = (RadioButton) getActivity().findViewById(selectedId);
    	String note = (String) radioButtonSelected.getText();
    	note = note.substring(0, 1);
    	
        String comment = ((EditText) this.getView().findViewById(R.id.comment_to_add)).getText().toString();
        
        // Basic local validation
        boolean error = false;
        if(comment.length() == 0)
        {
            ((EditText) this.getView().findViewById(R.id.comment_to_add)).setError(this.getString(R.string.comment_required));
            error = true;
        }
        
        if(error)
        {
            return;
        }
        
        PutCommentService putCommentService = new PutCommentService(getActivity());
        putCommentService.execute(idUserToString, code, comment, note);
    }
}
