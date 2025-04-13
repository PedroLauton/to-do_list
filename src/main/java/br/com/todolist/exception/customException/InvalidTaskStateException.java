package br.com.todolist.exception.customException;

public class InvalidTaskStateException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public InvalidTaskStateException(String message) {
		super(message);
	}
}
