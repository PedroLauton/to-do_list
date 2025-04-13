package br.com.todolist.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCompletedPatchDTO {
	
	private Boolean concluida; 
	
	public TaskCompletedPatchDTO() {
	}
	
	public Boolean getConcluida() {
		return concluida;
	}
	
	public void setConcluida(Boolean concluida) {
		this.concluida = concluida;
	}
}
