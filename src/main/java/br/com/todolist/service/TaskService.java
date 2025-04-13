package br.com.todolist.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.todolist.exception.customException.ResourceNotFoundException;
import br.com.todolist.model.Task;
import br.com.todolist.model.dtos.TaskCompletedPatchDTO;
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
		
		TaskResponseDTO taskResponseDTO = modelMapper.map(task, TaskResponseDTO.class);
	    return taskResponseDTO;
	}
	
	public Page<TaskResponseDTO> getAllTasksByCategoria(Pageable pageable, String categoria){
		Page<Task> tasks = taskRepository.findByCategoriaContainingIgnoreCase(pageable, categoria);
	    return tasks.map(task -> modelMapper.map(task, TaskResponseDTO.class));
	}
	
	public TaskResponseDTO completedTask(Long id, TaskCompletedPatchDTO taskCompletedPatchDTO){
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found by Id."));
		
		if(taskCompletedPatchDTO.getConcluida() != null) {
			task.setConcluida(taskCompletedPatchDTO.getConcluida());
		}
		
		Task taskSaved = taskRepository.save(task);
		TaskResponseDTO taskResponseDTO = modelMapper.map(taskSaved, TaskResponseDTO.class);
	    return taskResponseDTO;
	}
	
	public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, LocalDateTime requestDateTime) {
		Task task = modelMapper.map(taskRequestDTO, Task.class);
		task.setCriadaEm(requestDateTime);
		Task taskCreated = taskRepository.save(task);
		return modelMapper.map(taskCreated, TaskResponseDTO.class);
	}

}
