package com.rtaware.personinfo;

public class ContactInfo
{
	public ContactInfo(ContactType contactType, String contactSpecification)
	{
		super();
		this.contactType = contactType;
		this.contactSpecification = contactSpecification;
	}
	
	public ContactType getContactType()
	{
		return contactType;
	}
	public void setContactType(ContactType contactType)
	{
		this.contactType = contactType;
	}
	public String getContactSpecification()
	{
		return contactSpecification;
	}
	public void setContactSpecification(String contactSpecification)
	{
		this.contactSpecification = contactSpecification;
	}
	
	@Override
	public String toString()
	{
		return " {Contact Type : "+this.contactType+", Contact Specification : "+this.contactSpecification+"}";
	}

	private ContactType 	contactType				= null;
	private String 			contactSpecification	=	"";
}
