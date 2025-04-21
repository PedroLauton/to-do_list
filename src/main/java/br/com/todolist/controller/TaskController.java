package br.com.todolist.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.todolist.dto.model.TaskPatchDTO;
import br.com.todolist.dto.model.TaskRequestDTO;
import br.com.todolist.dto.model.TaskResponseDTO;
import br.com.todolist.dto.pagination.PaginationResponseDTO;
import br.com.todolist.exception.ErrorResponse;
import br.com.todolist.exception.MultiCauseResponse;
import br.com.todolist.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

/**
 * Controlador REST responsável por gerenciar Tasks.
 *
 * <p>Essa classe fornece 'endpoints' para criar, atualizar, listar e excluir tasks</p>
 * 
 * <p>Além disso, todos os métodos estão mapeados com o Swagger, fornecendo uma documentação
 * interativa de cada ação da API To-do List</p>
 * 
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	
	private final TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	/**
	 * Retorna todas as Tasks registradas com suporte à paginação.
	 * 
	 * @param Recebe um pageable, objeto que contém informações de paginação e ordenação.
	 * @return Retorna um ResponseEntity contendo um DTO com a lista de tarefas paginadas.
	 */
	@GetMapping
	@Operation(summary = "Buscar todas as Tasks", description  = "Realiza a busca de todas as Task com a exibição paginada.")
	@ApiResponse(responseCode = "200", description = "Retorna as Tasks.")
	public ResponseEntity<PaginationResponseDTO> getAllTasks(@Parameter(description = "Parâmetros de paginação (page, size, sort).") Pageable pageable){
		PaginationResponseDTO tasksReponseDTO = taskService.getAllTasks(pageable);
		return ResponseEntity.ok(tasksReponseDTO);
	}
	
	/**
	 * Retorna uma Task com base no ID fornecido..
	 * 
	 * @param Recebe o ID no formato Long.
	 * @return ResponseEntity contendo um DTO com a Task.
	 * @throws Retorna a exceção ResourceNotFoundException caso não encontre a Task.
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Buscar Task pelo ID.", description  = "Realiza a busca de uma Task com base no ID informado pelo cliente.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Retorna a Task."),
		@ApiResponse(responseCode = "404", description = "Task não encontrada.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))}
	)
	public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable @Parameter(description = "ID da Task") Long id){
		TaskResponseDTO taskResponseDTO = taskService.getTaskById(id);
		return ResponseEntity.ok(taskResponseDTO);
	}
	
	/**
	 * Retorna todas as Tasks que correspondem a categoria pesquisada.
	 * 
	 * @param Recebe um pageable, objeto que contém informações de paginação e ordenação.
	 * @param Recebe a Categoria no formato String.
	 * @return Retorna um ResponseEntity contendo um DTO com as Tasks paginadas.
	 */
	@GetMapping("/search")
	@Operation(summary = "Buscar Task pela categoria.", description  = "Realiza a busca de todas as Tasks com a categoria informada, exibindo o resultado de forma paginada.")
	@ApiResponse(responseCode = "200", description = "Retorna as Tasks.")
	public ResponseEntity<PaginationResponseDTO> getAllTasksByCategoria(Pageable pageable, @RequestParam String categoria){
		PaginationResponseDTO tasksResponseDTO = taskService.getAllTasksByCategoria(pageable, categoria);
		return ResponseEntity.ok(tasksResponseDTO);
	}
	
	/**
	 * Cadastra uma nova Task com os dados fornecidos.
	 * 
	 * @param Recebe um TaskRequestDTO com os dados da Task.
	 * @return Retorna um ResponseEntity contendo um DTO com a Task criada.
	 * @throws Retorna a exceção MethodArgumentNotValidException caso os dados do RequestDTO sejam inválidos.
	 */
	@PostMapping
	@Operation(summary = "Cadastrar uma Task.", description  = "Realiza o cadastro de uma nova Task com as informações concedidas pelo cliente.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Retorna a Task criada."),
		@ApiResponse(responseCode = "400", description = "Erro de validação dos atributos da Task.",
				content = @Content(schema = @Schema(implementation = MultiCauseResponse.class)))}
	)
	public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid @Parameter(description = "Informações para criação da Task") TaskRequestDTO taskRequestDTO){
		TaskResponseDTO taskResponseDTO = taskService.createTask(taskRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDTO);
	}
	
	/**
	 * Atualiza o status da Task.
	 * 
	 * @param Recebe o ID da Task no formato Long.
	 * @return Retorna um ResponseEntity contendo um DTO com a Task atualizada.
	 * @throws Retorna ResourceNotFoundException caso a Task não seja encontrada.
	 * @throws Retorna InvalidTaskStateException se a tarefa já estiver concluída
	 */
	@PatchMapping("/{id}/concluir")
	@Operation(summary = "Concluir uma Task.", description  = "Conclui-se uma Task.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Retorna a Task concluída."),
		@ApiResponse(responseCode = "404", description = "Task não encontrada.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))}
	)
	public ResponseEntity<TaskResponseDTO> completedTask(@PathVariable @Parameter(description = "ID da Task") Long id){
		TaskResponseDTO taskResponseDTO = taskService.completedTask(id);
		return ResponseEntity.ok(taskResponseDTO);
	}
	
	/**
	 * Atualiza completamente a Task com os dados fornecidos.
	 * 
	 * <p>Nesse método, todos os atributos devem ser atualizados. Não se pode deixar nenhum atributo de fora
	 * no corpo da requição</p>
	 * 
	 * @param Recebe o ID da Task no formato Long.
	 * @param Recebe um TaskRequestDTO com os dados a serem atualizados.
	 * @return Retorna um ResponseEntity contendo um DTO com a Task atualizada.
	 * @throws Retorna a exceção MethodArgumentNotValidException caso os dados do RequestDTO sejam inválidos.
	 * @throws Retorna a exceção ResourceNotFoundException caso a Task não seja encontrada.
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Atualizar completamente uma Task.", description  = "Realiza a atualização completa de uma Task")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Retorna a Task atualizada."),
		@ApiResponse(responseCode = "400", description = "Erro de validação dos atributos a serem atualizados.", 
				 content = @Content(schema = @Schema(implementation = MultiCauseResponse.class))),
		@ApiResponse(responseCode = "404", description = "Task não encontrada para atualização.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))}
	)
	public ResponseEntity<TaskResponseDTO> putTask(@PathVariable @Parameter(description = "ID da Task") Long id, @RequestBody @Parameter(description = "Informações para a atualização da Task") @Valid TaskRequestDTO taskRequestDTO){
		TaskResponseDTO taskResponseDTO = taskService.putTask(id, taskRequestDTO);
		return ResponseEntity.ok(taskResponseDTO);
	}
	
	/**
	 * Atualiza parcialmente a Task com os dados fornecidos.
	 * 
	 * <p>Nesse método, pode-se ocultar atributos a serem atualizados. Por exemplo, pode-se atualizar
	 * somente o título ou a descrição.</p>
	 * 
	 * @param Recebe o ID da Task no formato Long.
	 * @param Recebe um TaskRequestDTO com os dados a serem atualizados.
	 * @return Retorna um ResponseEntity contendo um DTO com a Task atualizada.
	 * @throws Retorna a exceção MethodArgumentNotValidException caso os dados do RequestDTO sejam inválidos.
	 * @throws Retorna a exceção ResourceNotFoundException caso a Task não seja encontrada.
	 */
	@PatchMapping("/{id}")
	@Operation(summary = "Atualizar parcialmente uma Task.", description  = "Realiza a atualização parcial de uma Task")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Retorna a Task atualizada."),
		@ApiResponse(responseCode = "400", description = "Erro de validação dos atributos a serem atualizados.",
				content = @Content(schema = @Schema(implementation = MultiCauseResponse.class))),
		@ApiResponse(responseCode = "404", description = "Task não encontrada para atualização.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))}
	)
	public ResponseEntity<TaskResponseDTO> patchTask(@PathVariable @Parameter(description = "ID da Task") Long id, @RequestBody @Parameter(description = "Informações para a atualização da Task") @Valid TaskPatchDTO taskPatchDTO){
		TaskResponseDTO taskResponseDTO = taskService.patchTask(id, taskPatchDTO);
		return ResponseEntity.ok(taskResponseDTO);
	}
	
	/**
	 * Deleta a Task com base no ID fornecido.
	 * 
	 * @param Recebe o ID da Task no formato Long.
	 * @return Retorna um ResponseEntity vazio, avisando que a execução ocorreu com sucesso.
	 * @throws Retorna ResourceNotFoundException caso a Task não seja encontrada.
	 * @throws Retorna InvalidTaskStateException se a tarefa já estiver concluída.
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar uma Task.", description  = "Deleta uma Task dos registros.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Confirmação de sucesso, mas sem devolver nada."),
		@ApiResponse(responseCode = "404", description = "Task não encontrada para deleção.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))}
	)
	public ResponseEntity<Void> deleteTask(@PathVariable @Parameter(description = "ID da Task") Long id){
		taskService.deleteTask(id);
		return ResponseEntity.noContent().build();
	}
}
