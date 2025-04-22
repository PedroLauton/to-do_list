package br.com.todolist.dto.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.todolist.enums.Prioridade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de resposta para uma Task.
 * 
 * <p>Essa classe é utilizada para retornar ao cliente os dados de uma Task somente,
 * seja após sua criação, atualização ou consulta.</p>
 * 
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {
	
    @Schema(description = "ID da Task", example = "1")
	private Long id;
	
    @Schema(description = "Título da Task", example = "Estudar Spring Boot")
	private String titulo;
    
    @Schema(description = "Descrição detalhada da Task", example = "Prestar atenção nas aulas do professor Giovani e fazer os exercícios.")
	private String descricao;
    
    @Schema(description = "Prioridade da Task (ALTA, MEDIA, BAIXA)", example = "ALTA")
	private Prioridade prioridade;
    
    @Schema(description = "Data limite para realizar a Task", example = "2025-04-30")
	private LocalDate dataLimite;
    
    @Schema(description = "Estado da Task", example = "true")
	private Boolean concluida; 
    
    @Schema(description = "Categoria da Task", example = "Faculdade")
	private String categoria;
    
    @Schema(description = "Data e horário de criação da Task", example = "2025-04-21T22:29:28")
	private LocalDateTime criadaEm;
	
	public TaskResponseDTO() {
	}
	
	public TaskResponseDTO(Long id, String titulo, String descricao, Prioridade prioridade, LocalDate dataLimite,
			Boolean concluida, String categoria, LocalDateTime criadaEm) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.prioridade = prioridade;
		this.dataLimite = dataLimite;
		this.concluida = concluida;
		this.categoria = categoria;
		this.criadaEm = criadaEm;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Prioridade getPrioridade() {
		return prioridade;
	}
	
	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}
	
	public LocalDate getDataLimite() {
		return dataLimite;
	}
	
	public void setDataLimite(LocalDate dataLimite) {
		this.dataLimite = dataLimite;
	}
	
	public Boolean getConcluida() {
		return concluida;
	}
	
	public void setConcluida(Boolean concluida) {
		this.concluida = concluida;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public LocalDateTime getCriadaEm() {
		return criadaEm;
	}
	
	public void setCriadaEm(LocalDateTime criadaEm) {
		this.criadaEm = criadaEm;
	}
}
