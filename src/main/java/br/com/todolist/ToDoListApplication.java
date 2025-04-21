package br.com.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Classe responsável pela inicialização da aplicação To-do List.
 * 
 * <p>Esta classe contém o método principal que inicia a aplicação Spring Boot e configura a documentação da API
 * usando Swagger.</p>
 *
 * <p>O Swagger será configurado para fornecer uma documentação interativa da API To-do List, incluindo o título,
 * a versão e a descrição.</p>
 *
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
@OpenAPIDefinition(info = @Info(title = "To-do List", version = "1.0", description = "Documentação da API To-do List"))
@SpringBootApplication
public class ToDoListApplication {
	
	/**
	 * Método principal para inicializar a aplicação Spring Boot.
	 * 
     * <p>Este método inicia a aplicação e configura o servidor embutido para executar a API To-do List.</p>
     * 
     * @param args os argumentos da linha de comando passados para a aplicação
	 */
	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}

}
