package br.com.eduardo.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface IGenericService<T> {

	List<T> findAll();

	T save(T t);

	T findById(Long id);

	void delete(T t);

	void deleteById(Long id);

	long count();
}
