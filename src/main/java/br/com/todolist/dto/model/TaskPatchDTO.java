package br.com.todolist.dto.model;

import java.time.LocalDate;
import java.util.Optional;

import br.com.todolist.model.enumeration.Prioridade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO responsável pela captação de dados.
 * 
 * <p>Essa clase tem como objetivo capturar os dados da requisição HTTP Patch. Seus atributos podem
 * ou não conter dados</p>
 * 
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPatchDTO {
	
    @Schema(description = "Título da Task", example = "Estudar Spring Boot")
	private Optional<String> titulo = Optional.empty();
    
    @Schema(description = "Descrição detalhada da Task", example = "Prestar atenção nas aulas do professor Giovani e fazer os exercícios.")
	private Optional<String> descricao = Optional.empty();;
	
    @Schema(description = "Prioridade da Task (ALTA, MEDIA, BAIXA)", example = "ALTA")
	private Optional<Prioridade> prioridade = Optional.empty();;
	
    @Schema(description = "Data limite para realizar a Task", example = "2025-04-30")
	private Optional<LocalDate> dataLimite = Optional.empty();;
	
    @Schema(description = "Estado da Task", example = "true")
	private Optional<Boolean> concluida = Optional.empty();; 
	
    @Schema(description = "Categoria da Task", example = "Faculdade")
	private Optional<String> categoria = Optional.empty();;
	
	public TaskPatchDTO() {
	}
	
	public TaskPatchDTO(Optional<String> titulo, Optional<String> descricao, Optional<Prioridade> prioridade,
			Optional<LocalDate> dataLimite, Optional<Boolean> concluida, Optional<String> categoria) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.prioridade = prioridade;
		this.dataLimite = dataLimite;
		this.concluida = concluida;
		this.categoria = categoria;
	}



	public Optional<String> getTitulo() {
		return titulo;
	}
	
	public void setTitulo(Optional<String> titulo) {
		this.titulo = titulo;
	}
	
	public Optional<String> getDescricao() {
		return descricao;
	}
	
	public void setDescricao(Optional<String> descricao) {
		this.descricao = descricao;
	}
	
	public Optional<Prioridade> getPrioridade() {
		return prioridade;
	}
	
	public void setPrioridade(Optional<Prioridade> prioridade) {
		this.prioridade = prioridade;
	}
	
	public Optional<LocalDate> getDataLimite() {
		return dataLimite;
	}
	
	public void setDataLimite(Optional<LocalDate> dataLimite) {
		this.dataLimite = dataLimite;
	}
	
	public Optional<Boolean> getConcluida() {
		return concluida;
	}
	
	public void setConcluida(Optional<Boolean> concluida) {
		this.concluida = concluida;
	}
	
	public Optional<String> getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Optional<String> categoria) {
		this.categoria = categoria;
	}
}
