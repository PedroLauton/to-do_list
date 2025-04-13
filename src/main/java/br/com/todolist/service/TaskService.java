package br.com.todolist.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.todolist.exception.customException.InvalidTaskStateException;
import br.com.todolist.exception.customException.ResourceNotFoundException;
import br.com.todolist.model.Task;
import br.com.todolist.model.dtos.TaskPatchDTO;
import br.com.todolist.model.dtos.TaskRequestDTO;
import br.com.todolist.model.dtos.TaskResponseDTO;
import br.com.todolist.repository.TaskRepository;

@Service
public class TaskService {
	
	private final TaskRepository taskRepository;
	private final ModelMapper modelMapper;
	
	public TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
		this.taskRepository = taskRepository;
		this.modelMapper = modelMapper;
	}
	
	public Page<TaskResponseDTO> getAllTasks(Pageable pageable){
		Page<Task> tasks = taskRepository.findAll(pageable);
	    return tasks.map(task -> modelMapper.map(task, TaskResponseDTO.class));
	}
	
	public TaskResponseDTO getTaskById(Long id){
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found by Id."));
		
		return modelMapper.map(task, TaskResponseDTO.class);
	}
	
	public Page<TaskResponseDTO> getAllTasksByCategoria(Pageable pageable, String categoria){
		Page<Task> tasks = taskRepository.findByCategoriaContainingIgnoreCase(pageable, categoria);
	    return tasks.map(task -> modelMapper.map(task, TaskResponseDTO.class));
	}
	
	public TaskResponseDTO completedTask(Long id){
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found by Id."));
		
		invalidTaskState(task);
		task.setConcluida(true);
		
		Task taskSaved = taskRepository.save(task);
		return modelMapper.map(taskSaved, TaskResponseDTO.class);
	}
	
	public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
		Task task = modelMapper.map(taskRequestDTO, Task.class);
		Task taskCreated = taskRepository.save(task);
		return modelMapper.map(taskCreated, TaskResponseDTO.class);
	}

	public TaskResponseDTO putTask(Long id, TaskRequestDTO taskRequestDTO) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found by Id."));
		
		invalidTaskState(task);
		modelMapper.map(taskRequestDTO, task);
		Task updatedTask = taskRepository.save(task);
		return modelMapper.map(updatedTask, TaskResponseDTO.class);
	}
	
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
	
	public void deleteTask(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found by Id."));
		
		invalidTaskState(task);
		taskRepository.deleteById(id);
	}
	
	private void invalidTaskState(Task task) {
		if(task.getConcluida()) {
			throw new InvalidTaskStateException("It is not possible to change or delete a completed task.");
		}
	}
}
