/*
 * class to define mark and comment
 */

package fr.utt.topuv.model;

public class Note {
	private int id;
	private int id_user;
	private int id_uv;
	private int note;
	private String last_name;
	private String first_name;
	private String comment;
	private String date;
    
	//Getter & Setter Id
  	public void setId(int inId)
  	{
  		this.note = inId;
  	}
  	
  	public int getId()
  	{
  		return this.id;
  	}
	
	//Getter & Setter Id User
  	public void setIdUser(int inIdUser)
  	{
  		this.id_user = inIdUser;
  	}
  	
  	public int getIdUser()
  	{
  		return this.id_user;
  	}
	
	//Getter & Setter Id Uv
  	public void setIdUv(int inIdUv)
  	{
  		this.id_uv = inIdUv;
  	}
  	
  	public int getIdUv()
  	{
  		return this.id_uv;
  	}
	
	//Getter & Setter Note
  	public void setNote(int inNote)
  	{
  		this.note = inNote;
  	}
  	
  	public int getNote()
  	{
  		return this.note;
  	}
  	
  	
  	//Getter & Setter Comment
  	public void setComment(String inComment)
  	{
  		this.comment = inComment;
  	}
  	
  	public String getComment()
  	{
  		return this.comment;
  	}
  	
  	
  	//Getter & Setter Date
  	public void setDate(String inDate)
  	{
  		this.date = inDate;
  	}
  	
  	public String getDate()
  	{
  		return this.date;
  	}
  	
  	
  	//Getter & Setter Lastname
  	public void setLastName(String inLastName)
  	{
  		this.last_name = inLastName;
  	}
  	
  	public String getLastName()
  	{
  		return this.last_name;
  	}
  	
  	
  	//Getter & Setter Firstname
  	public void setFirstName(String inFirstName)
  	{
  		this.first_name = inFirstName;
  	}
  	
  	public String getFirstName()
  	{
  		return this.first_name;
  	}
  	
  	
  	//Get full description lastname + firstname + date
  	public String getFullDescription()
  	{
  		return "by " + this.last_name + " " + this.first_name + " on " + this.date;
  	}
}
