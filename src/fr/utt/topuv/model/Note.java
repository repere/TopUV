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
  	
  	public String getFullDescription()
  	{
  		return "by" + this.last_name + " " + this.first_name + " on " + this.date;
  	}
}
