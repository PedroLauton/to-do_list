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
    
    @NotNull(message = "A tarefa deve ter o seu status informada.")
	private Boolean concluida; 
    
    @NotBlank(message = "A categoria da tarefa deve ser informada.")
	private String categoria;
    
	private LocalDateTime criadaEm;

    public Task() {
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
	
	@PrePersist
	private void dateTaskCreated() {
		criadaEm = LocalDateTime.now();
	}
}
