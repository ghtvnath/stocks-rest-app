package com.vish.payconiq.assignment.stocksrestapp.exception;

/**
 * @author Tharindu
 * 
 * <p>
 * This ErrorCode enum defines all the types of errors that will can be occurred during the application.
 * </P
 *
 */
public enum ErrorCode {
	// errors related to user inputs
	INPUT_ERROR(001, "Input Error."),
	
	// errors related to database operations
	APPLICATION_ERROR(100, "Application Error."), NO_DATA_ERROR(101, "No Data.");

	private final int code;
	private final String description;

	private ErrorCode(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code + ": " + description;
	}
}
