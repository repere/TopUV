/*
 * Control when user wants to add comment and rate for a specific UV
 * 
 * 
 */

package fr.utt.topuv.controller;

import java.util.concurrent.ExecutionException;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import fr.utt.topuv.R;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.UvSelected;
import fr.utt.topuv.service.UvService;

public class CommentController extends Fragment implements OnClickListener
{
    public interface MessageWriterFragmentListener
    {
        public void onMessageSent(UvSelected uvSelected);
    }

    private MessageWriterFragmentListener messageWriterFragmentListener;

    public void setMessageWriterFragmentListener(MessageWriterFragmentListener messageWriterFragmentListener)
    {
        this.messageWriterFragmentListener = messageWriterFragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = (View) inflater.inflate(R.layout.main_fragment_uv_comment, null);
        view.findViewById(R.id.send).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        try
        {
            String token = this.getActivity().getIntent().getStringExtra(IntentConstants.TOKEN);
            int contact = this.getActivity().getIntent().getIntExtra(IntentConstants.CONTACT, -1);
            String message = ((EditText) this.getView().findViewById(R.id.comment)).getText().toString();

            UvService uvService = new UvService();
            UvSelected messageObject = uvService.execute(token, String.valueOf(contact), message).get();

            ((EditText) this.getView().findViewById(R.id.comment)).setText("");
            
            if(messageObject != null)
            {
                this.messageWriterFragmentListener.onMessageSent(messageObject);
            }
            
            else
            {
                Toast.makeText(this.getActivity(), R.string.comment_not_sent, Toast.LENGTH_SHORT).show();
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
