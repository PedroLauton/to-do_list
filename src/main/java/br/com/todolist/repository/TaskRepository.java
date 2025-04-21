package br.com.todolist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todolist.model.Task;

/**
 * Interface responsável pela comunicação com o banco de dados para operações relacionadas à entidade Task.
 * 
 * <p>Estende a interface 'JpaRepository', herdando métodos padrão para operações CRUD.</p>
 * 
 * <p>Define também o método 'findByCategoriaContainingIgnoreCase', que permite buscar Tasks
 * com base em uma parte do nome da categoria, ignorando diferenças entre letras maiúsculas e minúsculas.</p>
 * 
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
public interface TaskRepository extends JpaRepository<Task, Long>{
	Page<Task> findByCategoriaContainingIgnoreCase(Pageable pageable, String Categoria);
}
