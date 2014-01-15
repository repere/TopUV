/*
 * Display list of comments link to the specific UV
 * 
 */

package fr.utt.topuv.controller;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import fr.utt.topuv.R;
import fr.utt.topuv.adapter.ListCommentAdapter;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.model.Note;
import fr.utt.topuv.model.Uv;
import fr.utt.topuv.sqlite.CommentDb;
import fr.utt.topuv.sqlite.UvDb;

public class ListCommentController extends Fragment implements OnItemClickListener
{	
	private String code;
	
	private ListView listViewComment;
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.list_comments_fragment, null);
		listViewComment = (ListView) viewGroup.findViewById(R.id.listViewComments);
    	listViewComment.setEmptyView(viewGroup.findViewById(R.id.listViewCommentsEmpty));
    	listViewComment.setOnItemClickListener(this);
		
		code = getActivity().getIntent().getStringExtra(IntentConstants.CODE);
    	
    	// Retrieve ID from SQlite db by their code
    	UvDb uvDb= new UvDb(getActivity().getApplicationContext());
    	uvDb.read();

    	Uv uv = uvDb.getUvByUvCode(code);
    	int uvId = uv.getId();

    	CommentDb commentDb= new CommentDb(getActivity().getApplicationContext());
    	commentDb.read();
    	
    	ArrayList<Note> arrayListNotes = commentDb.getCommentByUvId(uvId);

        ListCommentAdapter adapter = new ListCommentAdapter(this.getActivity().getApplicationContext(),R.layout.comments_list_entry, arrayListNotes);
            
        listViewComment.setAdapter(adapter);
        
        uvDb.close();
        commentDb.close();
		
		return viewGroup;
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	{
		Note noteSelected = (Note) parent.getItemAtPosition(position);
		int noteFromDbToRatingBar = (int) noteSelected.getNote();
		
		//Display rating bar in toast
		Toast.makeText(this.getActivity(), "Note donnée : " + String.valueOf(noteFromDbToRatingBar), Toast.LENGTH_SHORT).show();
	}
}
