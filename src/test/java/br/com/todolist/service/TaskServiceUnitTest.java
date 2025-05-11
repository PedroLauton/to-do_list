package br.com.todolist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.todolist.dto.model.TaskRequestDTO;
import br.com.todolist.dto.model.TaskResponseDTO;
import br.com.todolist.exception.customException.InvalidTaskStateException;
import br.com.todolist.model.Task;
import br.com.todolist.model.enumeration.Prioridade;
import br.com.todolist.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceUnitTest {
	
	@InjectMocks
	private TaskService taskService;
	
	@Mock
	private TaskRepository taskRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Test
	@DisplayName("Criação de Task com sucesso.")
	void deveCriarTarefaComSucesso_quandoDadosValidosForemInformados() {
		LocalDateTime dateTime = LocalDateTime.now();
		Long id = 1L;
		
	    TaskRequestDTO taskResquestDTO = new TaskRequestDTO("Atividade Giovani", "Desenvolver uma API To-do List", 
	    		Prioridade.ALTA, LocalDate.parse("2025-04-24"), false, "Faculdade");
	    
	    Task task = new Task("Atividade Giovani", "Desenvolver uma API To-do List", 
	    		Prioridade.ALTA, LocalDate.parse("2025-04-24"), false, "Faculdade", dateTime);
        ReflectionTestUtils.setField(task, "id", id); // ID via reflexão

	    TaskResponseDTO taskResponseDTO = new TaskResponseDTO(id, "Atividade Giovani", "Desenvolver uma API To-do List", 
	    		Prioridade.ALTA, LocalDate.parse("2025-04-24"), false, "Faculdade", dateTime);	    

	    when(modelMapper.map(taskResquestDTO, Task.class)).thenReturn(task);
	    when(taskRepository.save(task)).thenReturn(task);
	    when(modelMapper.map(task, TaskResponseDTO.class)).thenReturn(taskResponseDTO);
		
	    TaskResponseDTO taskCreatedDTO = taskService.createTask(taskResquestDTO);

	    assertEquals(id, taskCreatedDTO.getId());
	    assertEquals("Atividade Giovani", taskCreatedDTO.getTitulo());
	}
	
	@Test
	@DisplayName("Busca de Task pelo ID com sucesso.")
	void deveRetornarTarefa_quandoIdExistir() {
		LocalDateTime dateTime = LocalDateTime.now();
		Long id = 1L;
	    
	    Task task = new Task("Atividade Giovani", "Desenvolver uma API To-do List", 
	    		Prioridade.ALTA, LocalDate.parse("2025-04-24"), false, "Faculdade", dateTime);
        ReflectionTestUtils.setField(task, "id", id); // ID via reflexão

	    TaskResponseDTO taskResponseDTO = new TaskResponseDTO(id, "Atividade Giovani", "Desenvolver uma API To-do List", 
	    		Prioridade.ALTA, LocalDate.parse("2025-04-24"), false, "Faculdade", dateTime);	    

	    when(taskRepository.findById(id)).thenReturn(Optional.of(task));
	    when(modelMapper.map(task, TaskResponseDTO.class)).thenReturn(taskResponseDTO);
		
	    TaskResponseDTO foundTask = taskService.getTaskById(id);

	    assertEquals(id, foundTask.getId());
	    assertEquals("Atividade Giovani", foundTask.getTitulo());
	}
	
	@Test
	@DisplayName("Tentar excluir tarefa já concluída, resultando no lançamento da exceção InvalidTaskStateException.")
	void deveLancarExcecao_quandoATaskEstiverConcluida() {
		LocalDateTime dateTime = LocalDateTime.now();
		Long id = 1L;
	    
	    Task task = new Task("Atividade Giovani", "Desenvolver uma API To-do List", 
	    		Prioridade.ALTA, LocalDate.parse("2025-04-24"), true, "Faculdade", dateTime);
        ReflectionTestUtils.setField(task, "id", id); // ID via reflexão
		
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        
        assertThrows(InvalidTaskStateException.class, () -> taskService.deleteTask(id));
	}
}
