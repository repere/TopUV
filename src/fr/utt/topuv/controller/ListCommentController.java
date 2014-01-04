/*
 * Display list of comments link to the specific UV
 * Display additional informations when clicking on comments from other users
 * 
 */

package fr.utt.topuv.controller;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import fr.utt.topuv.R;
import fr.utt.topuv.adapter.ListCommentAdapter;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.Note;
import fr.utt.topuv.service.GetListCommentService;

public class ListCommentController extends ListFragment
{	
	private String code;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        try
        {
        	code = this.getActivity().getIntent().getStringExtra(IntentConstants.CODE);

            GetListCommentService getListCommentService = new GetListCommentService();
            ArrayList<Note> arrayListNotes = getListCommentService.execute(code).get();

            ListCommentAdapter adapter = new ListCommentAdapter(this.getActivity().getApplicationContext(),R.layout.comments_list_entry, arrayListNotes);
            
            this.setListAdapter(adapter);
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
		Note noteSelected = (Note) this.getListAdapter().getItem(position);
		int noteFromDbToRatingBar = (int) noteSelected.getNote();
		//Display rating bar in toast
		Toast.makeText(this.getActivity(), "Note donnée : " + String.valueOf(noteFromDbToRatingBar), Toast.LENGTH_SHORT).show();
    }
}
