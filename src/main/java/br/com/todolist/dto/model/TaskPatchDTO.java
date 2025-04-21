package br.com.todolist.dto.model;

import java.time.LocalDate;
import java.util.Optional;

import br.com.todolist.enums.Prioridade;
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
	
	private Optional<String> titulo = Optional.empty();
	private Optional<String> descricao = Optional.empty();;
	private Optional<Prioridade> prioridade = Optional.empty();;
	private Optional<LocalDate> dataLimite = Optional.empty();;
	private Optional<Boolean> concluida = Optional.empty();; 
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
