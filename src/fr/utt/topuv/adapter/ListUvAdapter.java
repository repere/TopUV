package fr.utt.topuv.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.utt.topuv.R;
import fr.utt.topuv.model.Uv;

public class  ListUvAdapter extends ArrayAdapter<Uv>
{
	int layout;
	
	public ListUvAdapter(Context context, int resource, ArrayList<Uv> uvs)
    {
        super(context, resource, uvs);
        layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this.getContext()).inflate(layout, null);

        ((TextView) viewGroup.findViewById(R.id.codeUvFromDB)).setText(this.getItem(position).code);
        ((TextView) viewGroup.findViewById(R.id.designationUvFromDB)).setText(this.getItem(position).designation);

        return viewGroup;
    }
}
