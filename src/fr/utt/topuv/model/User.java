/*
 * Class to define user
 */

package fr.utt.topuv.model;

public class User
{
    private static int id;
    private String token;
    private int success;
    private static String firstName;
    private static String lastName;
    
    //Getter & Setter First Name
  	public static void setFirstName(String inFirstName)
  	{
  		firstName = inFirstName;
  	}
  	
  	public static String getFirstName()
  	{
  		return User.firstName;
  	}
  	
  	//Getter & Setter Last Name
  	public static void setLastName(String inLastName)
  	{
  		User.lastName = inLastName;
  	}
  	
  	public static String getLastName()
  	{
  		return User.lastName;
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
  	public static void setId(int inId)
  	{
  		User.id = inId;
  	}
  	
  	public static int getId()
  	{
  		return User.id;
  	}
  	
  	
  	//Getter & Setter Success
  	public void setSuccess(int inSuccess)
  	{
  		this.success = inSuccess;
  	}
  	
  	public int getSuccess()
  	{
  		return this.success;
  	}
}