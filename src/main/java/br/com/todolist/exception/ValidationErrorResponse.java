package br.com.todolist.exception;

import java.util.Map;

import lombok.Data;

@Data
public class ValidationErrorResponse extends ErrorResponse {

	private Map<String, String> fieldErrors;

	public ValidationErrorResponse(int status, String errorTitle, String errorMessage, Map<String, String> fieldErrors) {
		super(status, errorTitle, errorMessage);
		this.fieldErrors = fieldErrors;
	}

	public Map<String, String> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(Map<String, String> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}
