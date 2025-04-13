package br.com.todolist.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.todolist.exception.customException.InvalidTaskStateException;
import br.com.todolist.exception.customException.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalHandlerException {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String,String>> handleConstraintViolationException(ConstraintViolationException exception){
		Map<String, String> errorResponse = new HashMap<>();
		exception.getConstraintViolations().forEach((error) -> {
			errorResponse.put(error.getPropertyPath().toString(), error.getMessage());
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(InvalidTaskStateException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<ErrorResponse> handleInvalidTaskStateException(InvalidTaskStateException exception){
		ErrorResponse error = new ErrorResponse(
				HttpStatus.CONFLICT.value(),
				"Invalid Task State",
				exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
		ErrorResponse error = new ErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				"Resource Not Found",
				exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
		
		Map<String,String> fieldsError = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			if(error instanceof FieldError) {
				fieldsError.put(((FieldError) error).getField(), error.getDefaultMessage());
			}
		});	
		
		ValidationErrorResponse error = new ValidationErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Data validation error",
				"Validation failed for one or more fields,",
				fieldsError);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception exception){
		ErrorResponse error = new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Unmapped error",
				exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
