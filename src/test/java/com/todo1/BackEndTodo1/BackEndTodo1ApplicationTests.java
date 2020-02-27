package com.todo1.BackEndTodo1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.todo1.BackEndTodo1.models.Products;
import com.todo1.BackEndTodo1.models.Transaction;
import com.todo1.BackEndTodo1.repository.ProductsRepository;
import com.todo1.BackEndTodo1.repository.TransactionRepository;

@SpringBootTest
class BackEndTodo1ApplicationTests {
	
	@Autowired
	private ProductsRepository productosRepository;
	
	@Autowired
    TransactionRepository transactionRepository;
		
	@Test
	void createProductTest() {
		Products prod = new Products();
		prod.setName("Camisa Angular Talla M");
		prod.setCode("123");
		prod.setStock(2);
		Products response = productosRepository.save(prod);
		assertTrue(Integer.toString(response.getId()).equalsIgnoreCase(Integer.toString(prod.getId())));
	}
	
	@Test
	void getProductTest() {
		List<Products> response = productosRepository.findAll();
		assertTrue(response.size() >= 0);
	}
	
	@Test
	void getTransactionTest() {
		List<Transaction> response = transactionRepository.findAll();
		assertTrue(response.size() >= 0);
	}
	
	@Test
	void createTransactionInputTest() {
		Transaction trans = new Transaction();
		trans.setType("inputs");
		trans.setCode("3");
		trans.setDate(new Date());
		trans.setStock(20);
		trans.setPrice(10000);
		Transaction response = transactionRepository.save(trans);
		assertTrue(Integer.toString(response.getId()).equalsIgnoreCase(Integer.toString(trans.getId())));
	}
	
	@Test
	void createTransactionOutputTest() {
		Transaction trans = new Transaction();
		trans.setType("outputs");
		trans.setCode("3");
		trans.setDate(new Date());
		trans.setStock(20);
		trans.setPrice(10000);
		Transaction response = transactionRepository.save(trans);
		assertTrue(Integer.toString(response.getId()).equalsIgnoreCase(Integer.toString(trans.getId())));
	}

}
