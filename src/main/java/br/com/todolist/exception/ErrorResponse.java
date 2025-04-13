package br.com.todolist.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {
	
	private LocalDateTime dateTime;
	private int status;
	private String errorTitle;
	private String errorMessage;
	
	public ErrorResponse(int status, String errorTitle, String errorMessage) {
		this.dateTime = LocalDateTime.now();
		this.status = status;
		this.errorTitle = errorTitle;
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getErrorTitle() {
		return errorTitle;
	}

	public void setErrorTitle(String errorTitle) {
		this.errorTitle = errorTitle;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
