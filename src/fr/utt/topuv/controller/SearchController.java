package fr.utt.topuv.controller;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import fr.utt.topuv.R;
import fr.utt.topuv.activity.SearchResultActivity;
import fr.utt.topuv.activity.UvActivity;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.sqlite.UvDb;

public class SearchController extends Fragment implements OnClickListener
{	
	private String[] allCodeUv;
	
	private String code;
	private String category = "";
	private String designation = "";
	private String description = "";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_search, null);
        viewGroup.findViewById(R.id.search_button_method1).setOnClickListener(this);
        viewGroup.findViewById(R.id.search_button_method2).setOnClickListener(this);
        
        //Populate auto complete textview
        UvDb uvDb= new UvDb(getActivity().getApplicationContext());
        uvDb.read();
        allCodeUv = uvDb.getAllUv();
        
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, allCodeUv);
        AutoCompleteTextView textView = (AutoCompleteTextView) viewGroup.findViewById(R.id.autoCompleteTextView1);
        textView.setAdapter(autoCompleteAdapter);   
        
        return viewGroup;
    }
	
	@Override
    public void onClick(View v)
    {	
		switch(v.getId())
		{
			case R.id.search_button_method1:
				searchByTheirCode();
				break;
				
			case R.id.search_button_method2:
				searchByOtherCriteria();
				break;
		}
    }
	
	private void searchByTheirCode()
	{
		code = ((AutoCompleteTextView) this.getView().findViewById(R.id.autoCompleteTextView1)).getText().toString();
		
		// Test if the field is empty
		boolean error = false;
		if(code.length() == 0)
        {
            ((EditText) this.getView().findViewById(R.id.autoCompleteTextView1)).setError(this.getString(R.string.search_uv_code_required));
            error = true;
        }
		
		if(error)
        {
            return;
        }
		
		// Test if the field contains UV which are in the autocompletetextview
		error = true;
		int lenght = allCodeUv.length;
        for(int i = 0; i<lenght; i++)
        {
        	if(allCodeUv[i] == code)
        	{
        		error = false;
        	}
        }
        
        if(error)
        {
        	((EditText) this.getView().findViewById(R.id.autoCompleteTextView1)).setError(this.getString(R.string.search_uv_code_unknow));
        	return;
        }   
		
		Intent intent = new Intent(this.getActivity(), UvActivity.class);
        intent.putExtra(IntentConstants.CODE, code);
        
        int idUser = this.getActivity().getIntent().getIntExtra(IntentConstants.ID_USER, 0);
        intent.putExtra(IntentConstants.ID_USER, idUser);
        
        this.startActivity(intent);
	}
	
	private void searchByOtherCriteria()
	{			
		Intent intent = new Intent(this.getActivity(), SearchResultActivity.class);
		
		String testNotNull;
		
		RadioGroup radioGroup = (RadioGroup) this.getView().findViewById(R.id.radioGroupUvCategory);
    	int selectedId = radioGroup.getCheckedRadioButtonId();
    	if(selectedId > -1)
    	{
    		RadioButton radioButtonSelected = (RadioButton) this.getView().findViewById(selectedId);
        	
        	testNotNull = (String) radioButtonSelected.getText();
        	
        	if(testNotNull.length() != 0)
        	{
        		category = testNotNull;
        	}
    	}
    	intent.putExtra(IntentConstants.CATEGORIE, category);
    	
    	
    	testNotNull = ((EditText) this.getView().findViewById(R.id.search_designation)).getText().toString();
    	if(testNotNull.length() != 0)
    	{
    		designation = testNotNull;
    	}
    	intent.putExtra(IntentConstants.DESIGNATION, designation);
    	
    	
    	testNotNull = ((EditText) this.getView().findViewById(R.id.search_description)).getText().toString();
    	if(testNotNull.length() != 0)
    	{
    		description = testNotNull;
    	}
    	intent.putExtra(IntentConstants.DESCRIPTION, description);
        
        int idUser = this.getActivity().getIntent().getIntExtra(IntentConstants.ID_USER, 0);
        intent.putExtra(IntentConstants.ID_USER, idUser);
        
        this.startActivity(intent);
	}
}
