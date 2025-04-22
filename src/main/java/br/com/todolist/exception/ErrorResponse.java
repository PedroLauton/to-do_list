package br.com.todolist.exception;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO responsável por representar de forma padronizada as respostas de erro da API.
 * 
 * <p>Esta classe é utilizada para encapsular informações relevantes sobre falhas
 * ocorridas durante o processamento de requisições, facilitando a comunicação
 * de erros entre o servidor e o cliente.</p>
 * 
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	
    @Schema(description = "Data e hora da ocorrência do erro", example = "2025-04-21T22:29:28")
	private LocalDateTime dateTime;
    
    @Schema(description = "Código HTTP de erro", example = "404")
	private int status;
    
    @Schema(description = "Título do erro", example = "EInvalid ID")
	private String errorTitle;
    
    @Schema(description = "Detalhamento do erro", example = "the ID provided for the task does not exist")
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
