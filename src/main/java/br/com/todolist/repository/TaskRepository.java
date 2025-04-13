package br.com.todolist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todolist.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	Page<Task> findByCategoriaContainingIgnoreCase(Pageable pageable, String Categoria);
}
