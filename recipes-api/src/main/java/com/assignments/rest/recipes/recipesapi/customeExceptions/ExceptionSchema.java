package com.assignments.rest.recipes.recipesapi.customeExceptions;

import java.time.LocalDateTime;

public class ExceptionSchema {
	private LocalDateTime timestamp;
	private String message;
	private String trace;
	
	public ExceptionSchema(LocalDateTime timestamp, String message, String trace) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.trace = trace;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getTrace() {
		return trace;
	}
}
