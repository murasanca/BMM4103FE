package com.murasanca.BMM4103FE.ui.sendMessage;

public class MessageClass
{
	Boolean isSelected=false;
	String message,name;
	
	public MessageClass(String message,String name)
	{
		this.message=message;
		this.name=name;
	}
	
	public Boolean getSelected()
	{
		return isSelected;
	}
	
	public void setSelected(Boolean selected)
	{
		isSelected=selected;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(String message)
	{
		this.message=message;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
}
