package br.com.todolist.model.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import br.com.todolist.enums.Prioridade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPatchDTO {
	
	private Optional<String> titulo;
	private Optional<String> descricao;
	private Optional<Prioridade> prioridade;
	private Optional<LocalDate> dataLimite;
	private Optional<Boolean> concluida; 
	private Optional<String> categoria;
	private Optional<LocalDateTime> criadaEm;
	
	public TaskPatchDTO() {
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
	public Optional<LocalDateTime> getCriadaEm() {
		return criadaEm;
	}
	
	public void setCriadaEm(Optional<LocalDateTime> criadaEm) {
		this.criadaEm = criadaEm;
	}
}
