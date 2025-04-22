package br.com.todolist.exception;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO responsável por representar respostas de erro com múltiplas causas
 * (normalmente associadas a falhas de validação de campos).
 * 
 * <p>Esta classe estende 'ErrorResponse' e adiciona o suporte ao mapeamento
 * de múltiplos erros específicos por campo, permitindo uma resposta mais
 * detalhada e precisa ao cliente.</p>
 * 
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
@Data
public class MultiCauseResponse extends ErrorResponse {
	
	@Schema(description = "Campos de erros, caso a exceção tenha detalhes específicos sobre os campos inválidos. A chave do mapa representa o nome do campo com erro, e o valor representa a mensagem de erro correspondente.")
	private Map<String, String> fieldErrors;

	public MultiCauseResponse(int status, String errorTitle, String errorMessage, Map<String, String> fieldErrors) {
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
