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
    public ListUvAdapter(Context context, ArrayList<Uv> uvs)
    {
        super(context, R.layout.uvs_list_entry, uvs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        int layout = R.layout.uvs_list_entry;

        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this.getContext()).inflate(layout, null);

        ((TextView) viewGroup.findViewById(R.id.codeUvFromDB)).setText(this.getItem(position).code);
        ((TextView) viewGroup.findViewById(R.id.designationUvFromDB)).setText(this.getItem(position).designation);

        return viewGroup;
    }
}
