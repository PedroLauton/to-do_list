package br.com.todolist.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerFunctionalTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	@DisplayName("Criar Task com dados válidos")
	void deveCriarTask_quandoOsDadosEstiveremCorretos() throws Exception {
		String json = """
					{
					  "titulo": "Estudar Spring Boot",
					  "descricao": "Revisar conceitos de Spring Boot",
					  "dataLimite": "2025-04-21",
					  "prioridade": "MEDIA",
					  "categoria": "Faculdade"
					}
				""";
		
		mockMvc.perform(post("/api/tasks")
                .contentType("application/json")
                .content(json))
				.andDo(print()) 
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.titulo").value("Estudar Spring Boot"))
				.andExpect(jsonPath("$.categoria").value("Faculdade"));
	}
	
	@Test
	@DisplayName("Criar Task com data limite inválida")
	void deveRetornarErro400_quandoADataLimiteForMenorQueHoje() throws Exception {
		String json = """
					{
					  "titulo": "Estudar Spring Boot",
					  "descricao": "Revisar conceitos de Spring Boot",
					  "dataLimite": "2025-04-19",
					  "prioridade": "MEDIA",
					  "categoria": "Faculdade"
					}
				""";
		
		mockMvc.perform(post("/api/tasks")
                .contentType("application/json")
                .content(json))
				.andDo(print()) 
				.andExpect(status().isBadRequest());	
	}
	
	@Test
	@DisplayName("Buscar Task com ID válido")
	void deveRetornarATask_quandoOIdEstiverCorreto() throws Exception {
		mockMvc.perform(get("/api/tasks/1"))
				.andDo(print()) 
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Alterar o status da Task para concluída.")
	void deveAlterarOStatusDaTask_quandoATaskNaoEstiverConcluida() throws Exception {
		mockMvc.perform(patch("/api/tasks/1/concluir"))
				.andDo(print()) 
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Verificar Erro de conflito quando tentar deletar Task concluída.")
	void deveRetornarErro409_quandoTentarExcluirTaskConcluida() throws Exception {
		mockMvc.perform(delete("/api/tasks/1"))
				.andDo(print()) 
				.andExpect(status().isConflict());
	}
	
	@Test
	@DisplayName("Retornar uma lista com as Tasks paginadas.")
	void deveRetorarUmaListaPaginadaComTodasAsTasks() throws Exception {
		mockMvc.perform(get("/api/tasks")
					.param("page", "0")
					.param("size", "2")
		            .contentType("application/json"))
				.andDo(print()) 
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.tasks").isArray())
				.andExpect(jsonPath("$.page").value("0"))
				.andExpect(jsonPath("$.size").value("2"))
				.andExpect(jsonPath("$.totalPages").value("1"))
				.andExpect(jsonPath("totalElements").value("1"));
	}
	
	@Test
	@DisplayName("Retornar uma lista com as Tasks paginadas por categoria.")
	void deveRetorarUmaListaPaginadaComTodasAsTaksPesquisadasPorCategoria() throws Exception {
		mockMvc.perform(get("/api/tasks/search")
					.param("categoria", "Faculdade")
					.param("page", "0")
					.param("size", "5")
		            .contentType("application/json"))
				.andDo(print()) 
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.tasks").isArray())
				.andExpect(jsonPath("$.tasks[0].categoria").value("Faculdade"));
	}
}
