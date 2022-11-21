package com.vfconverterapp.exceptions;

/**
 * DESC: Custom Exception which is thrown when the expected file format requested by the user is wrong
 * 
 * 		 (mp4, mkv, avi, mov) are the possible output formats. 
 * 
 * @author SivakumarK
 *
 */
public class WrongUserInputFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WrongUserInputFormatException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WrongUserInputFormatException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	

}
