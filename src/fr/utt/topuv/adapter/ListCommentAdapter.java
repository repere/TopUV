/*
 * A beautifull adapter to populate listview of comments more specifically  
 */

package fr.utt.topuv.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.utt.topuv.R;
import fr.utt.topuv.model.Note;

public class ListCommentAdapter extends ArrayAdapter<Note>
{
	int layout;
	
	public ListCommentAdapter(Context context, int resource, ArrayList<Note> notes)
    {
        super(context, resource, notes);
        layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this.getContext()).inflate(layout, null);

        ((TextView) viewGroup.findViewById(R.id.commentFromDb)).setText(this.getItem(position).getComment());
        ((TextView) viewGroup.findViewById(R.id.fullDescriptionFromDb)).setText(this.getItem(position).getFullDescription());

        return viewGroup;
    }
}
