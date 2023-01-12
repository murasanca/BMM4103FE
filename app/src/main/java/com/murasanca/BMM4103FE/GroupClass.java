package com.murasanca.BMM4103FE;

public class GroupClass
{
	String description,name;
	
	public void setDescription(String description)
	{
		this.description=description;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getName()
	{
		return name;
	}
	
	public GroupClass(String name,String description)
	{
		this.description=description;
		this.name=name;
	}
}
