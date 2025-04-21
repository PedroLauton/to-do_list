package br.com.todolist.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do ModelMapper para conversão de objetos.
 *
 * <p>Esta classe define um bean que pode ser injetado em qualquer
 * outra classe para realizar a conversão de objetos entre DTOs e entidades.</p>
 *
 * @author Pedro Lauton
 * @version 1.0
 * @since 20/04/2025
 */
@Configuration
public class MapperConfig {
	
	/**
	 * Cria e retorna uma instância de ModelMapper.
	 * 
	 * @return Retorna uma instância de ModelMapper.
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
