package com.rtaware.sacredthread.model;

public class ResourceInfo
{
	public String getResourceID()
	{
		return resourceID;
	}
	public void setResourceID(String resourceID)
	{
		this.resourceID = resourceID;
	}
	public String getResourceStatus()
	{
		return resourceStatus;
	}
	public void setResourceStatus(String resourceStatus)
	{
		this.resourceStatus = resourceStatus;
	}
	public String getResourceType()
	{
		return resourceType;
	}
	public void setResourceType(String resourceType)
	{
		this.resourceType = resourceType;
	}
	private String resourceID 			= "";
	private String resourceStatus 	= ""; //Waiting On, Waiting For , Locked
	private String resourceType		="";
}
