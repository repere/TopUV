/*
 * Control when user click on Send
 * Ask LoginService for login session
 * Launch MenuActivity if login/password is correct
 */

package fr.utt.topuv.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import fr.utt.topuv.R;
import fr.utt.topuv.service.LoginService;

public class LoginController extends Fragment implements OnClickListener
{	
	private LoginService loginService;
    
    private String login;
    private String password;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_login, null);
        viewGroup.findViewById(R.id.connect_button).setOnClickListener(this);

        return viewGroup;
    }
	
	@Override
    public void onClick(View v)
    {
		login = ((EditText) this.getView().findViewById(R.id.login)).getText().toString();
        password = ((EditText) this.getView().findViewById(R.id.password)).getText().toString();
        
        //Initiate login and user
        loginService = new LoginService(this.getActivity());

        // Basic local validation
        boolean error = false;
        if(password.length() == 0)
        {
            ((EditText) this.getView().findViewById(R.id.password)).setError(this.getString(R.string.password_required));
            error = true;
        }
        
        if(login.length() == 0)
        {
            ((EditText) this.getView().findViewById(R.id.login)).setError(this.getString(R.string.login_required));
            error = true;
        }
        
        if(error)
        {
            return;
        }
        
        loginService.execute(login, password);
    }
}