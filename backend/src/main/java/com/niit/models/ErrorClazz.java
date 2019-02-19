package com.niit.models;

public class ErrorClazz 
{
private int errorCode;
private String errorMessage;

public int getErrorCode() 
{
	return errorCode;
}
public void setErrorCode(int errorCode) 
{
	this.errorCode = errorCode;
}
public String getErrorMessage() 
{
	return errorMessage;
}
public void setErrorMessage(String errorMessage) 
{
	this.errorMessage = errorMessage;
}
public ErrorClazz(int errorCode, String errorMessage) 
{
	super();
	this.errorCode = errorCode;
	this.errorMessage = errorMessage;
}
}