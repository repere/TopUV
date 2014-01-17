package fr.utt.topuv.controller;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import fr.utt.topuv.R;
import fr.utt.topuv.activity.LoginActivity;
import fr.utt.topuv.service.CustomApplication;
import fr.utt.topuv.service.RegisterService;

public class RegisterController extends Fragment implements OnClickListener
{	
	private RegisterService registerService;
    
    private String first_name;
    private String last_name;
    private String email;
	private String login;
    private String password;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_registration, null);
        viewGroup.findViewById(R.id.register_button).setOnClickListener(this);
        viewGroup.findViewById(R.id.toLogin_button).setOnClickListener(this);

        return viewGroup;
    }

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.register_button:
				executeRegistration();
				break;
				
			case R.id.toLogin_button:
				executeToLogin();
				break;
		}
	}
	
	private void executeRegistration()
	{
		first_name = ((EditText) this.getView().findViewById(R.id.registerFirstName)).getText().toString();
	    last_name = ((EditText) this.getView().findViewById(R.id.registerLastName)).getText().toString();
	    email = ((EditText) this.getView().findViewById(R.id.registerEmail)).getText().toString();
		login = ((EditText) this.getView().findViewById(R.id.registerLogin)).getText().toString();
        password = ((EditText) this.getView().findViewById(R.id.registerPassword)).getText().toString();
        
        
        //Initiate login and user
        registerService = new RegisterService(this.getActivity());

        // Basic local validation
        boolean error = false;
        if(last_name.length() == 0)
        {
            ((EditText) this.getView().findViewById(R.id.registerLastName)).setError(this.getString(R.string.last_name_required));
            error = true;
        }
        
        if(first_name.length() == 0)
        {
            ((EditText) this.getView().findViewById(R.id.registerFirstName)).setError(this.getString(R.string.first_name_required));
            error = true;
        }
        
        if(email.length() == 0)
        {
            ((EditText) this.getView().findViewById(R.id.registerEmail)).setError(this.getString(R.string.email_required));
            error = true;
        }
        
        if(login.length() == 0)
        {
            ((EditText) this.getView().findViewById(R.id.registerLogin)).setError(this.getString(R.string.login_required));
            error = true;
        }
        
        if(password.length() == 0)
        {
            ((EditText) this.getView().findViewById(R.id.registerPassword)).setError(this.getString(R.string.password_required));
            error = true;
        }
        
        if(error)
        {
            return;
        }
        
        registerService.execute(last_name, first_name,  email, login, password);
	}
	
	private void executeToLogin()
	{
		Intent intent = new Intent(this.getActivity(), LoginActivity.class);

		this.getActivity().startActivity(intent);
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) 
    {
    	super.onSaveInstanceState(outState);
    	
    	((CustomApplication) getActivity().getApplication()).detach(getActivity());
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) 
    {
    	super.onActivityCreated(savedInstanceState);
    	
    	((CustomApplication) getActivity().getApplication()).attach(getActivity());
    }
}
