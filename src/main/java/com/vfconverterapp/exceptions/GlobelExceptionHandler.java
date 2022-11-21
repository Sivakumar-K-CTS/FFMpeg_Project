package com.vfconverterapp.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vfconverterapp.model.ApiErrors;


/**
 * DESC: Will manually handle the exceptions and providing all the required info when the exception occurred, by help of a model class named ApiErrors.
 * 
 * @author SivakumarK
 *
 */
@ControllerAdvice
public class GlobelExceptionHandler extends ResponseEntityExceptionHandler{

	/**
	 * @param ex
	 * @return: Will return the Exception to where it is called with all the customized informations.
	 */
	@ExceptionHandler(WrongUserInputFormatException.class)
	public ResponseEntity<Object> fileNotUploadedExceptions(WrongUserInputFormatException ex){
		String message = ex.getMessage();
		String error = "The Specified Output format is wrong please try again"; 
		ApiErrors apiError = new ApiErrors(LocalDateTime.now(), message, HttpStatus.CONFLICT , HttpStatus.CONFLICT.value(),error );
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", message);
		return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).body(apiError);
		
	}
	
	/**
	 * @param ex
	 * @return: Will return the Exception to where it is called with all the customized informations.
	 */
	@ExceptionHandler(FileFormatNotSupportedException.class)
	public ResponseEntity<Object> fileFormatNotSupportedException(FileFormatNotSupportedException ex){
		String message = ex.getMessage();
		String error = "Uplpoaded file format is not supported!!! Try again"; 
		ApiErrors apiError = new ApiErrors(LocalDateTime.now(), message, HttpStatus.CONFLICT , HttpStatus.CONFLICT.value(),error );
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", message);
		return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).body(apiError);
		
	}

}
