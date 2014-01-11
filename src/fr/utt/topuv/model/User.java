package fr.utt.topuv.model;

public class User
{
    private int id;
    private String token;
    private int success;
    private int idUserFromMysql;
    private String firstName;
    private String lastName;
    
    //Getter & Setter Id from Mysql
  	public void setidUserFromMysql(int inIdUserFromMysql)
  	{
  		this.idUserFromMysql = inIdUserFromMysql;
  	}
  	
  	public int getidUserFromMysql()
  	{
  		return this.idUserFromMysql;
  	}
    
    //Getter & Setter First Name
  	public void setFirstName(String inFirstName)
  	{
  		this.firstName = inFirstName;
  	}
  	
  	public String getFirstName()
  	{
  		return this.firstName;
  	}
  	
  	//Getter & Setter Last Name
  	public void setLastName(String inLastName)
  	{
  		this.lastName = inLastName;
  	}
  	
  	public String getLastName()
  	{
  		return this.lastName;
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
  	public void setId(int inId)
  	{
  		this.id = inId;
  	}
  	
  	public int getId()
  	{
  		return this.id;
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