package br.com.todolist.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.todolist.dto.model.TaskPatchDTO;
import br.com.todolist.dto.model.TaskRequestDTO;
import br.com.todolist.dto.model.TaskResponseDTO;
import br.com.todolist.dto.pagination.PaginationResponseDTO;
import br.com.todolist.exception.customException.InvalidTaskStateException;
import br.com.todolist.exception.customException.ResourceNotFoundException;
import br.com.todolist.model.Task;
import br.com.todolist.repository.TaskRepository;

/**
 * Classe de serviço responsável por aplicar as regras de negócio da aplicação ToDoList.
 * 
 * <p>Realiza o processamento das operações relacionadas à entidade 'Task', como criação, atualização,
 * exclusão, marcação de conclusão, listagem paginada e filtragem por categoria.</p>
 * 
 * <p>Utiliza ModelMapper para conversão entre entidades e DTOs, promovendo desacoplamento entre as
 * camadas da aplicação.</p>
 * 
 * <p>Também aplica validações, como verificação de tentativa de alteração em tarefas já concluídas.</p>
 * 
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
@Service
public class TaskService {
	
	private final TaskRepository taskRepository;
	private final ModelMapper modelMapper;
	
	public TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
		this.taskRepository = taskRepository;
		this.modelMapper = modelMapper;
	}
	
	/**
	 * Retorna todas as Tasks registradas com suporte à paginação.
	 * 
	 * @param Recebe um pageable, objeto que contém informações de paginação e ordenação.
	 * @return Retorna um PaginationResponseDTO que contém uma lista de Tasks paginadas.
	 */
	public PaginationResponseDTO getAllTasks(Pageable pageable){
		
		if(pageable.getSort() != null) {
			
		}
		
		Page<Task> tasks = taskRepository.findAll(pageable);
		
	    return new PaginationResponseDTO(tasks);
	}
	
	/**
	 * Retorna uma Task com base no ID fornecido.
	 * 
	 * @param Recebe o ID no formato Long.
	 * @return TaskResponseDTO com os detalhes da Task.
	 * @throws Retorna a exceção ResourceNotFoundException caso não encontre a Task.
	 */
	public TaskResponseDTO getTaskById(Long id){
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found by Id."));
		
		return modelMapper.map(task, TaskResponseDTO.class);
	}
	
	/**
	 * Retorna uma lista paginada de Tasks que correspondem a categoria pesquisada.
	 * 
	 * @param Recebe um pageable, objeto que contém informações de paginação e ordenação.
	 * @param Recebe a Categoria no formato String.
	 * @return Retorna um PaginationResponseDTO que contém uma lista de Tasks paginadas.
	 */
	public PaginationResponseDTO getAllTasksByCategoria(Pageable pageable, String categoria){
		Page<Task> tasks = taskRepository.findByCategoriaContainingIgnoreCase(pageable, categoria);
	    return new PaginationResponseDTO(tasks);
	}
	
	/**
	 * Cadastra uma nova Task com os dados fornecidos.
	 * 
	 * @param Recebe um TaskRequestDTO com os dados da Task.
	 * @return Retorna um TaskResponseDTO contendo os dados da Task criada.
	 * @throws Retorna MethodArgumentNotValidException caso os dados da entidade Task sejam inválidos.
	 */
	public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
		Task task = modelMapper.map(taskRequestDTO, Task.class);
		Task taskCreated = taskRepository.save(task);
		return modelMapper.map(taskCreated, TaskResponseDTO.class);
	}
	
	/**
	 * Atualiza o status da Task.
	 * 
	 * @param Recebe o ID da Task no formato Long.
	 * @return Retorna um TaskResponseDTO com os detalhes da Task atualizada.
	 * @throws Retorna ResourceNotFoundException caso a Task não seja encontrada.
	 * @throws Retorna InvalidTaskStateException se a tarefa já estiver concluída
	 */
	public TaskResponseDTO completedTask(Long id){
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found by Id."));
		
		invalidTaskState(task);
		task.setConcluida(true);
		
		Task taskSaved = taskRepository.save(task);
		return modelMapper.map(taskSaved, TaskResponseDTO.class);
	}
	
	/**
	 * Atualiza completamente a Task com os dados fornecidos.
	 * 
	 * @param Recebe o ID da Task no formato Long.
	 * @param Recebe um TaskRequestDTO com os dados a serem atualizados.
	 * @return Retorna um TaskResponseDTO contendo os detalhes da Task atualizada.
	 * @throws Retorna MethodArgumentNotValidException caso os dados da entidade Task sejam inválidos.
	 * @throws Retorna ResourceNotFoundException caso a Task não seja encontrada.
	 * @throws Retorna InvalidTaskStateException se a tarefa já estiver concluída
	 */
	public TaskResponseDTO putTask(Long id, TaskRequestDTO taskRequestDTO) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found by Id."));
		
		invalidTaskState(task);
		modelMapper.map(taskRequestDTO, task);
		Task updatedTask = taskRepository.save(task);
		return modelMapper.map(updatedTask, TaskResponseDTO.class);
	}
	
	/**
	 * Atualiza parcialmente os dados de uma tarefa com base nos dados fornecidos.
	 * 
	 * @param Recebe o ID da Task no formato Long.
	 * @param Recebe um TaskRequestDTO com os dados a serem atualizados.
	 * @return Retorna um TaskResponseDTO contendo os detalhes da Task atualizada.
	 * @throws Retorna MethodArgumentNotValidException caso os dados da entidade Task sejam inválidos.
	 * @throws Retorna ResourceNotFoundException caso a Task não seja encontrada.
	 * @throws Retorna InvalidTaskStateException se a tarefa já estiver concluída
	 */
	public TaskResponseDTO patchTask(Long id, TaskPatchDTO taskPatchDTO) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found by Id."));
		
		invalidTaskState(task);
		taskPatchDTO.getTitulo().ifPresent(task::setTitulo);
		taskPatchDTO.getPrioridade().ifPresent(task::setPrioridade);
		taskPatchDTO.getDescricao().ifPresent(task::setDescricao);
		taskPatchDTO.getDataLimite().ifPresent(task::setDataLimite);
		taskPatchDTO.getConcluida().ifPresent(task::setConcluida);
		taskPatchDTO.getCategoria().ifPresent(task::setCategoria);

		Task updatedTask = taskRepository.save(task);
		return modelMapper.map(updatedTask, TaskResponseDTO.class);
	}
	
	/**
	 * Deleta a Task com base no ID fornecido.
	 * 
	 * @param Recebe o ID da Task no formato Long.
	 * @return Retorna um ResponseEntity vazio, avisando que a execução ocorreu com sucesso.
	 * @throws Retorna ResourceNotFoundException caso a Task não seja encontrada.
	 * @throws Retorna InvalidTaskStateException se a tarefa já estiver concluída.
	 */
	public void deleteTask(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found by Id."));
		
		invalidTaskState(task);
		taskRepository.deleteById(id);
	}
	
	/**
	 * Valida se uma tarefa está concluída e lança uma exceção caso esteja.
	 * 
	 * @param task tarefa a ser verificada.
	 * @throws InvalidTaskStateException se a tarefa já estiver concluída.
	 */
	private void invalidTaskState(Task task) {
		if(task.getConcluida()) {
			throw new InvalidTaskStateException("It is not possible to change or delete a completed task.");
		}
	}
}
