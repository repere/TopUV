/*
 * Display list of comments link to the specific UV
 * Display additional informations when clicking on comments from other users
 * 
 */

package fr.utt.topuv.controller;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import fr.utt.topuv.R;
import fr.utt.topuv.adapter.ListCommentAdapter;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.Note;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.sqlite.CommentDb;
import fr.utt.topuv.sqlite.UvDb;

public class ListCommentController extends ListFragment
{	
	private String code;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        
    	code = this.getActivity().getIntent().getStringExtra(IntentConstants.CODE);
    	
    	// Retrieve ID from SQlite db by their code
    	UvDb uvDb= new UvDb(getActivity().getApplicationContext());
    	uvDb.read();
    	
    	//not a very clean method...
    	ArrayList<Uv> arrayListUvs  =  uvDb.getIdUvByUvCode(code);
    	
    	int uvId = arrayListUvs.get(0).getId();
    	
    	// Collects arraylist of notes thanks to the id of uv previously retrieved
    	CommentDb commentDb= new CommentDb(getActivity().getApplicationContext());
    	commentDb.read();
    	
    	ArrayList<Note> arrayListNotes = commentDb.getCommentByCodeUv(uvId);

        ListCommentAdapter adapter = new ListCommentAdapter(this.getActivity().getApplicationContext(),R.layout.comments_list_entry, arrayListNotes);
            
        this.setListAdapter(adapter);
        
        uvDb.close();
        commentDb.close();
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
