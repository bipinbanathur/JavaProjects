package com.rtaware.examples;


public class Product
{	
	public String getProductName()
	{
		return productName;
	}


	public void setProductName(String productName)
	{
		this.productName = productName;
	}


	public Integer getProductID()
	{
		return productID;
	}


	public void setProductID(Integer productID)
	{
		this.productID = productID;
	}


	public Double getProductPrice()
	{
		return productPrice;
	}


	public void setProductPrice(Double productPrice)
	{
		this.productPrice = productPrice;
	}


	private String 	productName = "";
	private Integer productID = 0;
	private Double 	productPrice = (Double) 0.0;
	
	public Product(Integer productID, String productName, Double productPrice)
	{
		this.productID 		= productID;
		this.productName  	= productName;
		this.productPrice	= productPrice;
		
	}
	public Product()
	{
		
	}
	
	@Override
	public String toString()
	{
		return "{"
				+ "\"ProductID\" : "+this.getProductID()+" , "
			    + "\"ProductName\": \""+this.getProductID()+"\","
				+ "\"ProductID\" : "+this.getProductPrice()+" } ";
	}
}
