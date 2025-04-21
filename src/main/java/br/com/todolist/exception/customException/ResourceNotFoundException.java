package br.com.todolist.exception.customException;

/**
 * Exceção personalizada lançada quando uma Task não é encontrada pelo ID.
 * 
 * <p>Essa exceção é utilizada para sinalizar que a entidade requisitada não existe no sistema.</p>
 * 
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
