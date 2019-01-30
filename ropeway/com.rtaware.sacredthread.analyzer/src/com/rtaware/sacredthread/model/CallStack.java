package com.rtaware.sacredthread.model;

public class CallStack
{
	public String getPackageName()
	{
		return packageName;
	}
	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}
	public String getClassName()
	{
		return className;
	}
	public void setClassName(String className)
	{
		this.className = className;
	}
	public String getLineNumber()
	{
		return lineNumber;
	}
	public void setLineNumber(String lineNumber)
	{
		this.lineNumber = lineNumber;
	}
	private String packageName 	= 	"";
	private String className		=	"";
	private String lineNumber 		=	"'";
	
}
