package com.vfconverterapp.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

/**
 * DESC: Model class used during the Exception handling
 * 		 To store required details as a model class to provide it as response 
 * 
 * @author SivakumarK
 *
 */
public class ApiErrors {
	LocalDateTime time;
	String message;
	HttpStatus status;
	int statusCode;
	String error;

	public ApiErrors() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApiErrors(LocalDateTime time, String message, HttpStatus status, int statusCode, String error) {
		super();
		this.time = time;
		this.message = message;
		this.status = status;
		this.statusCode = statusCode;
		this.error = error;
	}

	/**
	 * @return the time
	 */
	public LocalDateTime getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

}
