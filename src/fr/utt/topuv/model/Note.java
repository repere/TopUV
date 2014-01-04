package fr.utt.topuv.model;

public class Note {
	private float note;
	private String last_name;
	private String first_name;
	private String comment;
	private String date;
    
    //Getter & Setter Note
  	public void setNote(float inNote)
  	{
  		this.note = inNote;
  	}
  	
  	public float getNote()
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
  	
  	public String getFirst()
  	{
  		return this.first_name;
  	}
  	
  	
  	//Get full description lastname + firstname + date
  	public String getFullDescription()
  	{
  		return "by " + this.last_name + " " + this.first_name + " on " + this.date;
  	}
}
