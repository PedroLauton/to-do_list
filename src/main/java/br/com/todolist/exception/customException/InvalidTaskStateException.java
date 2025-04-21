package br.com.todolist.exception.customException;

/**
 * Exceção personalizada lançada quando há uma tentativa de atualizar
 * uma Task que já foi marcada como concluída.
 * 
 * <p>Essa exceção é utilizada para garantir a integridade da lógica de negócios,
 * impedindo modificações indevidas em tarefas finalizadas.</p>
 * 
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
public class InvalidTaskStateException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public InvalidTaskStateException(String message) {
		super(message);
	}
}
