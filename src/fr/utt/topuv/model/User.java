package fr.utt.topuv.model;

public class User
{
    private String id;
    private String first_name;
    private String last_name;
    private String token;

    public String getIdentity()
    {
        return this.first_name + " " + this.last_name;
    }
    
    //Getter & Setter Token
  	public void setToken(String inToken)
  	{
  		this.token = inToken;
  	}
  	
  	public String getToken()
  	{
  		return this.token;
  	}
  	
  	
  	//Getter & Setter Id
  	public void setId(String inId)
  	{
  		this.id = inId;
  	}
  	
  	public String getId()
  	{
  		return this.id;
  	}
}