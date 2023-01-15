// Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE;

public class SelectableGroupClass
{
	Boolean isSelected=false;
	String description,name;
	
	public SelectableGroupClass(String name,String description)
	{
		this.description=description;
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
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description=description;
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

// 201913709082
// Murat Sancak