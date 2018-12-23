package com.rtaware.bulkloader.model;

public class FlowVariable
{
	
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
	public String getAssingWrom()
	{
		return assingWrom;
	}
	public void setAssingWrom(String assingWrom)
	{
		this.assingWrom = assingWrom;
	}
	public String getWhenToAssign()
	{
		return whenToAssign;
	}
	public void setWhenToAssign(String whenToAssign)
	{
		this.whenToAssign = whenToAssign;
	}
	public String getExpressionValue()
	{
		return expressionValue;
	}
	public void setExpressionValue(String expressionValue)
	{
		this.expressionValue = expressionValue;
	}
	public String getDataFormat()
	{
		return dataFormat;
	}
	public void setDataFormat(String dataFormat)
	{
		this.dataFormat = dataFormat;
	}
	public String getConnectionName()
	{
		return connectionName;
	}
	public void setConnectionName(String connectionName)
	{
		this.connectionName = connectionName;
	}
	@Override
	public String toString()
	{
		return "FlowVariable [flowID=" + flowID + ", stepID=" + stepID + ", variableName=" + variableName
				+ ", variableType=" + variableType + ", assingWrom=" + assingWrom + ", whenToAssign=" + whenToAssign
				+ ", expressionValue=" + expressionValue + ", dataFormat=" + dataFormat + ", connectionName="
				+ connectionName + "]";
	}
	private  String flowID	        = "";
	private  String stepID	        = "";
	private  String variableName	= "";
	private  String variableType	= "";
	private  String assingWrom	    = "";
	private  String whenToAssign	= "";
	private  String expressionValue = "";
	private  String dataFormat	    = "";
	private  String connectionName	= "";

}
