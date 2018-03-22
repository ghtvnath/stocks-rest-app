package com.vish.payconiq.assignment.stocksrestapp.model;

/**
 * 
 * @author Tharindu
 * 
 * This will be used to return error response back to the client
 *
 */
public class ErrorResponse {
	
	private String errorCode;
	private String errorMessage;
	
	public ErrorResponse(){}
	
	public ErrorResponse(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
