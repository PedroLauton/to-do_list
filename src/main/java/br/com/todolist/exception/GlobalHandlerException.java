package br.com.todolist.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.todolist.exception.customException.InvalidTaskStateException;
import br.com.todolist.exception.customException.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;

/**
 * Classe responsável por tratar exceções lançadas globalmente na aplicação.
 * 
 * <p>Utiliza a anotação @RestControllerAdvice para capturar e tratar exceções
 * lançadas pelos controladores, retornando respostas padronizadas com informações relevantes
 * ao cliente.</p>
 * 
 * <p>Cada método lida com um tipo específico de exceção e retorna um 'ResponseEntity'
 * com a estrutura apropriada, como 'ErrorResponse' ou 'MultiCauseResponse', além do
 * código HTTP correspondente.</p>
 * 
 * @author Pedro
 * @version 1.0
 * @since 20/04/2025
 */
@RestControllerAdvice
public class GlobalHandlerException {

	/**
	 * Trata erros de desserialização JSON (ex: tipo inválido, formato incorreto).
	 * 
	 * @param Exception exceção capturada.
	 * @return Retorna ResponseEntity<ErrorResponse> com status 400 (Bad Request).
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
	    ErrorResponse errorResponse = new ErrorResponse(
	    		HttpStatus.BAD_REQUEST.value(),
	    		"Deserialization error.",
	    		exception.getMessage()
	    		);
	    
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	/**
	 * Trata violações de restrições de validação (ex: {@code @NotNull}, {@code @Size}, etc.).
	 * 
	 * @param Exception exceção capturada.
	 * @return Retorna ResponseEntity<MultiCauseResponse> com status 400 (Bad Request).
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<MultiCauseResponse> handleConstraintViolationException(ConstraintViolationException exception){
		Map<String,String> fieldsError = new HashMap<>();
		exception.getConstraintViolations().forEach((error) -> {
			fieldsError.put(error.getPropertyPath().toString(), error.getMessage());
		});
		
		MultiCauseResponse error = new MultiCauseResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Operation failed due to constraint violation.",
				"some fields contain invalid or inconsistent data.",
				fieldsError);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	/**
	 * Trata exceção personalizada para tentativa de alteração de tarefa já concluída.
	 * 
	 * @param Exception exceção capturada.
	 * @return Retorna ResponseEntity<ErrorResponse> com status 409 (Conflict).
	 */
	@ExceptionHandler(InvalidTaskStateException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<ErrorResponse> handleInvalidTaskStateException(InvalidTaskStateException exception){
		ErrorResponse error = new ErrorResponse(
				HttpStatus.CONFLICT.value(),
				"Invalid Task State",
				exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}
	
	/**
	 * Trata exceção personalizada para recursos não encontrados.
	 * 
	 * @param Exception exceção capturada.
	 * @return Retorna ResponseEntity<ErrorResponse> com status 404 (Not Found).
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
		ErrorResponse error = new ErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				"Resource Not Found",
				exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	/**
	 * Trata erros de validação de argumentos anotados com {@code @Valid} em DTOs.
	 * 
	 * @param Exception exceção capturada.
	 * @return Retorna ResponseEntity<MultiCauseResponse> com status 400 (Bad Request).
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
		
		Map<String,String> fieldsError = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			if(error instanceof FieldError) {
				fieldsError.put(((FieldError) error).getField(), error.getDefaultMessage());
			}
		});	
		
		MultiCauseResponse error = new MultiCauseResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Data validation error.",
				"Validation failed for one or more fields.",
				fieldsError);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	/**
	 * Trata qualquer exceção genérica não mapeada especificamente.
	 * 
	 * @param Exception exceção capturada.
	 * @return Retorna ResponseEntity<ErrorResponse> com status 500 (Internal Server Error).
	 */
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
