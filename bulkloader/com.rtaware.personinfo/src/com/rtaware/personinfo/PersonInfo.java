package com.rtaware.personinfo;

import java.util.Date;
import java.util.List;

public class PersonInfo
{
	public PersonInfo(String personFirstName, String personLastName, Date personDOB, List<ContactInfo> contactDetails)
	{
		super();
		this.personFirstName = personFirstName;
		this.personLastName = personLastName;
		this.personDOB = personDOB;
		this.contactDetails = contactDetails;
	}
	
	public PersonInfo()
	{
	}
	public String getPersonFirstName()
	{
		return personFirstName;
	}
	public void setPersonFirstName(String personFirstName)
	{
		this.personFirstName = personFirstName;
	}
	public String getPersonLastName()
	{
		return personLastName;
	}
	public void setPersonLastName(String personLastName)
	{
		this.personLastName = personLastName;
	}
	public Date getPersonDOB()
	{
		return personDOB;
	}
	public void setPersonDOB(Date personDOB)
	{
		this.personDOB = personDOB;
	}
	public List<ContactInfo> getContactDetails()
	{
		return contactDetails;
	}
	public void setContactDetails(List<ContactInfo> contactDetails)
	{
		this.contactDetails = contactDetails;
	}
	private String 					personFirstName 	= "";
	private String 					personLastName 	= "";
	private Date   					personDOB 			= null;
	private List<ContactInfo> 	contactDetails;
}
