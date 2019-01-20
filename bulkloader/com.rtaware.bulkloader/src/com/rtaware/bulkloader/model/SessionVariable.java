package com.rtaware.bulkloader.model;

public class SessionVariable
{
	public String getVariableName()
	{
		return variableName;
	}
	public void setVariableName(String variableName)
	{
		this.variableName = variableName;
	}
	public String getVariableValue()
	{
		return variableValue;
	}
	public void setVariableValue(String variableValue)
	{
		this.variableValue = variableValue;
	}
	public String getVariableType()
	{
		return variableType;
	}
	public void setVariableType(String variableType)
	{
		this.variableType = variableType;
	}
	public String getVariableComment()
	{
		return variableComment;
	}
	public void setVariableComment(String variableComment)
	{
		this.variableComment = variableComment;
	}
	
	public SessionVariable(String variableName, String variableValue, String variableType, String variableComment)
	{
		super();
		this.variableName = variableName;
		this.variableValue = variableValue;
		this.variableType = variableType;
		this.variableComment = variableComment;
	}
	
	private String variableName 	= "";
	private String variableValue 	= "";
	private String variableType 	= "";
	private String variableComment 	= "";
	
}
