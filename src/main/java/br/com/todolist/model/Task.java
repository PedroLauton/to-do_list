package br.com.todolist.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.todolist.enums.Prioridade;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade responsável pelo mapeamento e persistência de tarefas no banco de dados.
 * 
 * <p>Contém os atributos necessários para representar uma tarefa, como título, descrição, 
 * prioridade, data limite, status de conclusão, categoria e a data de criação.</p>
 * 
 * <p>Regras de validação são aplicadas via anotações da Bean Validation para garantir 
 * a integridade dos dados enviados pelo cliente.</p>
 * 
 * <p>O campo criadaEm é automaticamente populado no momento da persistência 
 * da entidade através do método @PrePersist.</p>
 * 
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotBlank(message = "O título não pode estar vazio.")
    @Size(min = 1, max = 20, message = "O título deve conter entre 1 e 20 caracteres.")
	private String titulo;
    
    @NotNull(message = "A descrição é opcional, mas não pode ser nula. Implemente a descrição com o campo vazio na requisição.")
	private String descricao;
	
    @NotNull(message = "A prioridade não deve ser nula. Escolha entre: ALTA, MEDIA ou BAIXA.")
	@Enumerated(EnumType.STRING)
	private Prioridade prioridade;
	
    @NotNull(message = "A data limite deve ser informada.")
    @Future(message = "A data limite deve ser uma data futura.")
	private LocalDate dataLimite;
    
	private Boolean concluida = false; 
    
    @NotBlank(message = "A categoria da tarefa deve ser informada.")
	private String categoria;
    
	private LocalDateTime criadaEm;

    public Task() {
    }
    
	public Task(String titulo, String descricao, Prioridade prioridade, LocalDate dataLimite, Boolean concluida,  String categoria, LocalDateTime criadaEm) {
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
	
	@PrePersist
	private void dateTaskCreated() {
		criadaEm = LocalDateTime.now();
	}
}
