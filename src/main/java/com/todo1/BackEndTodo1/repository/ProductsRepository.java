package com.todo1.BackEndTodo1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.todo1.BackEndTodo1.models.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
	List<Products> findByCodeOrNameContainingOrderByNameDesc(String code, String name);
	List<Products> findByCodeContainingOrderByCodeDesc(String code);
	List<Products> findByNameContainingOrderByNameDesc(String name);
	List<Products> findByCode(String code);
	boolean existsByCode(String code);
}
