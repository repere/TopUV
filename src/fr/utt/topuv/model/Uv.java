package fr.utt.topuv.model;

public class Uv
{
	private int id;
	public String code;
	private String designation;
	private int credit;
	private String description;
	private int note;
	private String cat;
	
	//Getter & Setter Id
	public void setId(int inId)
	{
		this.id = inId;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	
	//Getter & Setter Code
	public void setCode(String inCode)
	{
		this.code = inCode;
	}
	
	public String getCode()
	{
		return this.code;
	}
	
	
	//Getter & Setter Designation
	public void setDesignation(String inDesignation)
	{
		this.designation = inDesignation;
	}
	
	public String getDesignation()
	{
		return this.designation;
	}
	
	
	//Getter & Setter Credit
	public void setCredit(int inCredit)
	{
		this.credit = inCredit;
	}
	
	public int getCredit()
	{
		return this.credit;
	}
	
	
	//Getter & Setter Description
	public void setDescription(String inDescription)
	{
		this.description = inDescription;
	}
	
	public String getDescription()
	{
		return this.description;
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
	
	
	//Getter & Setter Description
	public void setCat(String inCat)
	{
		this.cat = inCat;
	}
	
	public String getCat()
	{
		return this.cat;
	}
}