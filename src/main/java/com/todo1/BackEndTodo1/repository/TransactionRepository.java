package com.todo1.BackEndTodo1.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.todo1.BackEndTodo1.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	List<Transaction> findByTypeAndCodeAndDateOrderByIdDesc(String type, String code, Date date);
	
}