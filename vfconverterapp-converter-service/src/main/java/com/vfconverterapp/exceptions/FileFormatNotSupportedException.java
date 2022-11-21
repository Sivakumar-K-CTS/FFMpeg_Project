package com.vfconverterapp.exceptions;

/**
 * DESC: Custom Exception which is thrown when the input file provided by the user is not a video file or the format which is not accepted by the api 
 * 
 * 		 (mp4, mkv, avi, mov) are the accepted formats. 
 * 
 * @author SivakumarK
 *
 */
public class FileFormatNotSupportedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileFormatNotSupportedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileFormatNotSupportedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	

}
