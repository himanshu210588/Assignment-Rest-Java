package com.assignments.rest.recipes.recipesapi.customeExceptions;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.assignments.rest.recipes.recipesapi.repository.RecipeDaoService;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
	
	// Get logger for this class
	Logger logger = LoggerFactory.getLogger(RecipeDaoService.class);
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		ExceptionSchema exceptionSchema = new ExceptionSchema(LocalDateTime.now(), 
				"Total Validation Errors:" + ex.getErrorCount() + " First Error:" + ex.getFieldError().getDefaultMessage(), request.getDescription(false));
		
		logger.error("Total Validation Errors:" + ex.getErrorCount() + " First Error:" + ex.getFieldError().getDefaultMessage());
		
		return new ResponseEntity(exceptionSchema, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RecipeNotFoundException.class)
	public final ResponseEntity<ExceptionSchema> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		ExceptionSchema exceptionSchema = new ExceptionSchema(LocalDateTime.now(), 
				ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ExceptionSchema>(exceptionSchema, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionSchema> handleGenericExceptions(Exception ex, WebRequest request) throws Exception {
		ExceptionSchema exceptionSchema = new ExceptionSchema(LocalDateTime.now(), 
				ex.getMessage(), request.getDescription(false));
		
		logger.error("Generic exception "+ex.getMessage());
		
		return new ResponseEntity<ExceptionSchema>(exceptionSchema, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
