/*
 * Add controller on tab : THIS SECTION WILL CHANGE FOR MORE USER FRIENDLY SWAP SYSTEM
 * 
 * 
 */

package fr.utt.topuv.controller;

import fr.utt.topuv.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class MenuController extends Fragment implements OnClickListener
{
	public static final int CS = 0;
	public static final int TM = 1;
	public static final int AUTRES = 2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_main_menu_bar, null);
		
		//Set click event listeners
		viewGroup.findViewById(R.id.csTab).setOnClickListener(this);
		viewGroup.findViewById(R.id.tmTab).setOnClickListener(this);
		viewGroup.findViewById(R.id.autresTab).setOnClickListener(this);
		return viewGroup;
	}


	public void setActive(int tab)
	{
		//Reset active tab by adding normal tab
		this.getView().findViewById(R.id.csTab).setBackgroundResource(R.drawable.main_menu_button);
		this.getView().findViewById(R.id.tmTab).setBackgroundResource(R.drawable.main_menu_button);
		this.getView().findViewById(R.id.autresTab).setBackgroundResource(R.drawable.main_menu_button);	
		
		// Choose which one which going to be active 
		switch(tab)
		{
			case CS:
				this.getView().findViewById(R.id.csTab).setBackgroundResource(R.drawable.main_menu_button_active);
			break;
			case TM:
				this.getView().findViewById(R.id.tmTab).setBackgroundResource(R.drawable.main_menu_button_active);
			break;
			case AUTRES:
				this.getView().findViewById(R.id.autresTab).setBackgroundResource(R.drawable.main_menu_button_active);
			break;
		}
	}
	
	@Override
	public void onClick(View view)
	{
		
		int tab = -1;
		
		switch(view.getId())
		{
			case R.id.csTab:
				tab = CS;
				//Manage first tab
			break;
				
			case R.id.tmTab:
				tab = TM;
				//Manage second tab
			break;
			
			case R.id.autresTab:
				tab = AUTRES;
				//Manage third tab
			break;
		}
		
		this.setActive(tab);
	}
}
