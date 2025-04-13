package br.com.todolist.model.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.todolist.enums.Prioridade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {
	
	private Long id;
	private String titulo;
	private String descricao;
	private Prioridade prioridade;
	private LocalDate dataLimite;
	private Boolean concluida; 
	private String categoria;
	private LocalDateTime criadaEm;
	
	public TaskResponseDTO() {
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
