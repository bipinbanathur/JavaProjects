package com.rtaware.sacredthread.model;

import java.util.ArrayList;
import java.util.List;

public class StackTrace
{
	public ThreadStatus getThreadStatus()
	{
		return threadStatus;
	}
	public void setThreadStatus(ThreadStatus threadStatus)
	{
		this.threadStatus = threadStatus;
	}
	public List<ResourceInfo> getResourceDetails()
	{
		return resourceDetails;
	}
	public void setResourceDetails(List<ResourceInfo> resourceDetails)
	{
		this.resourceDetails = resourceDetails;
	}
	public List<CallStack> getStackList()
	{
		return stackList;
	}
	public void setStackList(List<CallStack> stackList)
	{
		this.stackList = stackList;
	}
	public String getOnEntity()
	{
		return onEntity;
	}
	public void setOnEntity(String onEntity)
	{
		this.onEntity = onEntity;
	}
	private ThreadStatus 			threadStatus;
	private String						onEntity		=	"";
	private List<ResourceInfo> 	resourceDetails = new ArrayList<>();
	private List<CallStack> 			stackList = new ArrayList<>();
}
