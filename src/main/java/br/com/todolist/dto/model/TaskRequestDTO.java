package br.com.todolist.dto.model;

import java.time.LocalDate;

import br.com.todolist.enums.Prioridade;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO responsável pela captação de dados.
 * 
 * <p>Essa clase tem como objetivo capturar os dados enviados nas requisições Post e Put. Alguns atributos tem validações
 * de acordo com as regras de negócio da API.</p>
 * 
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {
	
    @NotBlank(message = "O título não pode estar vazio.")
    @Size(min = 1, max = 20, message = "O título deve conter entre 1 e 20 caracteres.")
	private String titulo;
    
    @NotBlank(message = "A descrição é opcional, mas não pode ser nula. Implemente a descrição com o campo vazio na requisição.")
	private String descricao;
	
    @NotNull(message = "A prioridade não deve ser nula. Escolha entre: ALTA, MEDIA ou BAIXA.")
	private Prioridade prioridade;
	
    @NotNull(message = "A data limite deve ser informada.")
    @Future(message = "A data limite deve ser uma data futura.")
	private LocalDate dataLimite;
    
	private Boolean concluida = false; 
    
    @NotBlank(message = "A categoria da tarefa deve ser informada.")
	private String categoria;

    public TaskRequestDTO() {
    }
    
	public TaskRequestDTO(String titulo, String descricao, Prioridade prioridade, LocalDate dataLimite, Boolean concluida, String categoria) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.prioridade = prioridade;
		this.dataLimite = dataLimite;
		this.concluida = concluida;
		this.categoria = categoria;
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
}
