package fr.utt.topuv.controller;

import java.util.concurrent.ExecutionException;

import fr.utt.topuv.activity.MenuActivity;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.service.PutCommentService;
import fr.utt.topuv.R;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UvCommentController extends Fragment implements OnClickListener
{
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_uv_comment, null);
        viewGroup.findViewById(R.id.send_comment_button).setOnClickListener(this);
                
        return viewGroup;
    }
	
	
    @Override
    public void onClick(View v)
    {
        
    	try
        {
            String code = this.getActivity().getIntent().getStringExtra(IntentConstants.CODE);
    		String idUser = this.getActivity().getIntent().getStringExtra(IntentConstants.ID_USER);
        	
        	RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup1);
	    	int selectedId = radioGroup.getCheckedRadioButtonId();
	    	RadioButton radioButtonSelected = (RadioButton) getActivity().findViewById(selectedId);
	    	String note = (String) radioButtonSelected.getText();
	    	note = note.substring(0, 1);
        	
            String comment = ((EditText) this.getView().findViewById(R.id.comment_to_add)).getText().toString();
            
            PutCommentService putCommentService = new PutCommentService();
            String result = putCommentService.execute(idUser, code, comment, note).get();
            
            if(result == null || result.equals("0"))
            {
            	Toast.makeText(this.getActivity(), R.string.comment_not_sent, Toast.LENGTH_SHORT).show();
            }
            else
            {
        		Intent intent = new Intent(this.getActivity(), MenuActivity.class);
                
                intent.putExtra(IntentConstants.ID_USER, idUser);
                this.startActivity(intent);
                this.getActivity().finish();
            }
        }
        catch(InterruptedException interruptedException)
        {

        }
        catch(ExecutionException executionException)
        {

        }
        catch(NullPointerException nullPointerException)
        {

        }
    }
}
