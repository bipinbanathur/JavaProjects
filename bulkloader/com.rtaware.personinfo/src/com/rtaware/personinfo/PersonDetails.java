package com.rtaware.personinfo;

import java.util.ArrayList;
import java.util.List;

public class PersonDetails
{
	private List<PersonInfo> personDetails = new ArrayList<>();

	public List<PersonInfo> getPersonDetails()
	{
		return personDetails;
	}

	public void setPersonDetails(List<PersonInfo> personDetails)
	{
		this.personDetails = personDetails;
	}

	public PersonDetails(List<PersonInfo> personDetails)
	{
		this.personDetails = personDetails;
	}
	
	public PersonDetails()
	{
	}
}
