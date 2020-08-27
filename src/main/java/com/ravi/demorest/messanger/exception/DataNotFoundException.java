package com.ravi.demorest.messanger.exception;

public class DataNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2447498665881367486L;

	public DataNotFoundException(String message){
		super(message);
	}
}
