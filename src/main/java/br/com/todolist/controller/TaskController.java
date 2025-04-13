package br.com.todolist.controller;

import org.springframework.data.domain.Page;
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

import br.com.todolist.model.dtos.TaskPatchDTO;
import br.com.todolist.model.dtos.TaskRequestDTO;
import br.com.todolist.model.dtos.TaskResponseDTO;
import br.com.todolist.service.TaskService;
import jakarta.validation.Valid;

// Vou comentar em breve todo o código 
 
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	
	private final TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@GetMapping
	public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(Pageable pageable){
		Page<TaskResponseDTO> tasksResponseDTO = taskService.getAllTasks(pageable);
		return ResponseEntity.ok(tasksResponseDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id){
		TaskResponseDTO taskResponseDTO = taskService.getTaskById(id);
		return ResponseEntity.ok(taskResponseDTO);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<TaskResponseDTO>> getAllTasksByCategoria(Pageable pageable, @RequestParam String categoria){
		Page<TaskResponseDTO> tasksResponseDTO = taskService.getAllTasksByCategoria(pageable, categoria);
		return ResponseEntity.ok(tasksResponseDTO);
	}
	
	@PostMapping
	public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid TaskRequestDTO taskRequestDTO){
		TaskResponseDTO taskResponseDTO = taskService.createTask(taskRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDTO);
	}
	
	@PatchMapping("/{id}/concluir")
	public ResponseEntity<TaskResponseDTO> completedTask(@PathVariable Long id){
		TaskResponseDTO taskResponseDTO = taskService.completedTask(id);
		return ResponseEntity.ok(taskResponseDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> putTask(@PathVariable Long id, @RequestBody @Valid TaskRequestDTO taskRequestDTO){
		TaskResponseDTO taskResponseDTO = taskService.putTask(id, taskRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDTO);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> patchTask(@PathVariable Long id, @RequestBody @Valid TaskPatchDTO taskPatchDTO){
		TaskResponseDTO taskResponseDTO = taskService.patchTask(id, taskPatchDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id){
		taskService.deleteTask(id);
		return ResponseEntity.noContent().build();
	}
}
