/*
 * Control when user click on Send
 * Ask LoginService for login session
 * Launch MenuActivity if login/password is correct
 */

package fr.utt.topuv.controller;

import java.util.concurrent.ExecutionException;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import fr.utt.topuv.R;
import fr.utt.topuv.activity.MenuActivity;
import fr.utt.topuv.constant.IntentConstants;
import fr.utt.topuv.service.LoginService;

public class LoginController extends Fragment implements OnClickListener
{
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_login, null);
        viewGroup.findViewById(R.id.connect).setOnClickListener(this);

        return viewGroup;
    }

    @Override
    public void onClick(View v)
    {    	    	
    	/* For testing code without logging
    	Intent intent = new Intent(this.getActivity(), MenuActivity.class);
        this.startActivity(intent);
        this.getActivity().finish();
        */
        
    	String login = ((EditText) this.getView().findViewById(R.id.login)).getText().toString();
        String password = ((EditText) this.getView().findViewById(R.id.password)).getText().toString();

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
        
        LoginService loginService = new LoginService(this.getActivity());
        try
        {
            String token = loginService.execute(login, password).get();
            
            //test if token from db exists
            if(token != null)
            {
                Intent intent = new Intent(this.getActivity(), MenuActivity.class);
                
                // Add student token to constant
                intent.putExtra(IntentConstants.TOKEN, token);
                this.startActivity(intent);
                this.getActivity().finish();
            }
            
            else
            {
                Toast.makeText(this.getActivity(), R.string.connexion_error, Toast.LENGTH_SHORT).show();
            }
        }
        
        catch(NullPointerException nullPointerException)
        {

        }
        
        catch (InterruptedException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        catch (ExecutionException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}