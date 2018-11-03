package com.rtaware.config;

public class Variable
{
	private String flowID			= "";
	private String stepID			= "";	
	private String variableName		= "";
	private String variableType		= "";
	private String variableValue	= "";
	private String accessType		= "";
	private String from				= "";
	private String to				= "";
	private String when				= "";
	private String expression		= "";
	private String format			= "";
	private String connectionName	= "";
	
	public String getConnectionName()
	{
		return connectionName;
	}

	public void setConnectionName(String connectionName)
	{
		this.connectionName = connectionName;
	}
	
	public String getFormat()
	{
		return format;
	}
	
	public void setFormat(String format)
	{
		this.format = format;
	}
	
	public String getExpression()
	{
		return expression;
	}
	
	public void setExpression(String expression)
	{
		this.expression = expression;
	}
	
	public String getFlowID()
	{
		return flowID;
	}
	
	public void setFlowID(String flowID)
	{
		this.flowID = flowID;
	}
	
	public String getStepID()
	{
		return stepID;
	}

	public void setStepID(String stepID)
	{
		this.stepID = stepID;
	}

	public String getVariableName()
	{
		return variableName;
	}

	public void setVariableName(String variableName)
	{
		this.variableName = variableName;
	}
	
	public String getVariableType()
	{
		return variableType;
	}
	
	public void setVariableType(String variableType)
	{
		this.variableType = variableType;
	}
	
	public String getVariableValue()
	{
		return variableValue;
	}
	
	public void setVariableValue(String variableValue)
	{
		this.variableValue = variableValue;
	}
	
	public String getAccessType()
	{
		return accessType;
	}
	
	public void setAccessType(String accessType)
	{
		this.accessType = accessType;
	}
	
	public String getFrom()
	{
		return from;
	}
	
	public void setFrom(String from)
	{
		this.from = from;
	}
	
	public String getTo()
	{
		return to;
	}
	
	public void setTo(String to)
	{
		this.to = to;
	}
	
	public String getWhen()
	{
		return when;
	}
	
	public void setWhen(String when)
	{
		this.when = when;
	}

}
