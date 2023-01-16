 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.addMembers2Group;

public class ContactClass
{
	Boolean isSelected=false;
	String contact,phone;
	
	public ContactClass(String contact,String phone)
	{
		this.contact=contact;
		this.phone=phone;
	}
	
	public String getContact()
	{
		return contact;
	}
	
	public void setContact(String contact)
	{
		this.contact=contact;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public void setPhone(String phone)
	{
		this.phone=phone;
	}
	
	public Boolean getSelected()
	{
		return isSelected;
	}
	
	public void setSelected(Boolean selected)
	{
		isSelected=selected;
	}
}

 // 201913709082
// Murat Sancak