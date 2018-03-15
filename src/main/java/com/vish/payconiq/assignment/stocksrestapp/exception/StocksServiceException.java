package com.vish.payconiq.assignment.stocksrestapp.exception;

public class StocksServiceException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private final ErrorCode code;
	
	public StocksServiceException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.code = code;
    }
	
	public StocksServiceException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }
	
	public ErrorCode getCode() {
        return this.code;
    }

}
