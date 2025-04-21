package br.com.todolist.dto.pagination;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.todolist.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO responsável por encapsular a resposta paginada de tarefas.
 * 
 * <p>Utilizado para retornar uma lista de Tasks de forma paginada ao cliente,
 * contendo também informações de controle como número da página, tamanho da página,
 * total de páginas e total de elementos.</p>
 * 
 * <p>A ordenação dos campos no JSON de resposta segue a seguinte ordem: 
 * tasks, page, size, totalPages, totalElements.</p>
 * 
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "tasks", "page", "size", "totalPages", "totalElements" })
public class PaginationResponseDTO {
	
	private List<Task> tasks;
	private Integer page;
	private Integer size;
	private Integer totalPages;
	private Long totalElements;
	
	public PaginationResponseDTO() {
	}
	
	public PaginationResponseDTO(List<Task> tasks, Integer page, Integer size, Integer totalPages, Long totalElements) {
		this.tasks = tasks;
		this.page = page;
		this.size = size;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
	}



	public PaginationResponseDTO(Page<Task> page) {
		this.tasks = page.getContent();
		this.page = page.getNumber();
		this.size = page.getSize();
		this.totalPages = page.getTotalPages();
		this.totalElements = page.getTotalElements();
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}
}
