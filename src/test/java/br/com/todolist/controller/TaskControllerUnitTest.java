package br.com.todolist.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.todolist.dto.model.TaskRequestDTO;
import br.com.todolist.dto.model.TaskResponseDTO;
import br.com.todolist.model.enumeration.Prioridade;
import br.com.todolist.service.TaskService;

@ExtendWith(MockitoExtension.class)
public class TaskControllerUnitTest {
	
	@InjectMocks
	private TaskController taskController;
	
	@Mock
	private TaskService taskService;
	
	@Test
	@DisplayName("Criação de Task com sucesso.")
	void deveCriarTaskComSucesso_quandoDadosValidosForemInformados() {
	    Long id = 1L;
	    TaskRequestDTO taskResquestDTO = new TaskRequestDTO("Atividade Giovani", "Desenvolver uma API To-do List", 
	    		Prioridade.ALTA, LocalDate.parse("2025-04-24"), false, "Faculdade");
	    
	    TaskResponseDTO taskResponseDTO = new TaskResponseDTO(id, "Atividade Giovani", "Desenvolver uma API To-do List", 
	    		Prioridade.ALTA, LocalDate.parse("2025-04-24"), false, "Faculdade", LocalDateTime.now());	    

	    when(taskService.createTask(taskResquestDTO)).thenReturn(taskResponseDTO);
		
	    ResponseEntity<TaskResponseDTO> taskCreated = taskController.createTask(taskResquestDTO);

	    assertEquals(HttpStatus.CREATED, taskCreated.getStatusCode());
	    assertEquals("Atividade Giovani", taskCreated.getBody().getTitulo());
	}
	
	@Test
	@DisplayName("Busca de Task pelo ID com sucesso.")
	void deveRetornarTarefa_quandoIdExistir() {
		Long id = 1L;

		TaskResponseDTO taskResponseDTO = new TaskResponseDTO(id, "Atividade Giovani", "Desenvolver uma API To-do List", 
	    		Prioridade.ALTA, LocalDate.parse("2025-04-24"), false, "Faculdade", LocalDateTime.now());	    

		when(taskService.getTaskById(id)).thenReturn(taskResponseDTO);
			
		ResponseEntity<TaskResponseDTO> foundTask = taskController.getTaskById(id);

		assertEquals(HttpStatus.OK, foundTask.getStatusCode());
		assertEquals(id, foundTask.getBody().getId());
	}
}
