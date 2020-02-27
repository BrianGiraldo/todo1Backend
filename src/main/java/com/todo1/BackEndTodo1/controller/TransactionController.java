package com.todo1.BackEndTodo1.controller;

import java.util.Date;
import java.util.List;

import javax.el.MethodNotFoundException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.todo1.BackEndTodo1.models.Products;
import com.todo1.BackEndTodo1.models.Transaction;
import com.todo1.BackEndTodo1.repository.ProductsRepository;
import com.todo1.BackEndTodo1.repository.TransactionRepository;

@RestController
public class TransactionController {
	
	@Autowired
    TransactionRepository transactionRepository;
	
	@Autowired
    ProductsRepository productsRepository;
	
	//OBTENER LAS TRANSACCIONES
	@GetMapping("/transactions")
	public List<Transaction> getTransaction(@RequestParam(name="type",required=false) String type, @RequestParam(name="code",required=false) String code,  @RequestParam(name="date",required=false) Date date) {
    	try {
    		return transactionRepository.findByTypeAndCodeAndDateOrderByIdDesc(type, code, date);
    	} catch (MethodNotFoundException exc) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transacciones no encontradas", exc);
        }
    }
	
	//REGISTRAR UNA TRANSACCION
	@SuppressWarnings("unchecked")
	@PostMapping("/create_transaction")
	public ResponseEntity<JSONObject> createTransaction(@RequestBody @Validated Transaction transaction) {
		JSONObject response = new JSONObject();
		List<Products> prod = productsRepository.findByCode(transaction.getCode());
		if(transaction.getType().contentEquals("inputs")) {
			if(!prod.isEmpty()) {
				transaction.setDate(new Date());
				Transaction operation = transactionRepository.save(transaction);
				response.put("status", HttpStatus.OK);
				response.put("status_code", "200");
				response.put("message", "Transaccion registrada");
				response.put("data", operation);
				Products products = new Products();
				products.setId(prod.get(0).getId());
				products.setStock(transaction.getStock()+prod.get(0).getStock());
				products.setName(prod.get(0).getName());
				products.setCode(prod.get(0).getCode());
				productsRepository.save(products);
				return new ResponseEntity<JSONObject>(response, HttpStatus.OK);
			} else {
				response.put("status", HttpStatus.BAD_REQUEST);
				response.put("status_code", "400");
				response.put("message", "No se puede registrar la transacción, el producto no existe");
				return new ResponseEntity<JSONObject>(response, HttpStatus.BAD_REQUEST);
			}
		}
		if(transaction.getType().contentEquals("outputs")) {
			if(!prod.isEmpty()) {
				if(prod.get(0).getStock() > 0) {
					if(prod.get(0).getStock() >= transaction.getStock()) {
						transaction.setDate(new Date());
						Transaction operation = transactionRepository.save(transaction);
						response.put("status", HttpStatus.OK);
						response.put("status_code", "200");
						response.put("message", "Transaccion registrada");
						response.put("data", operation);
						Products products = new Products();
						products.setId(prod.get(0).getId());
						products.setStock(prod.get(0).getStock()-transaction.getStock());
						products.setName(prod.get(0).getName());
						products.setCode(prod.get(0).getCode());
						productsRepository.save(products);
						return new ResponseEntity<JSONObject>(response, HttpStatus.OK);
					} else {
						response.put("status", HttpStatus.BAD_REQUEST);
						response.put("status_code", "400");
						response.put("message", "No se puede registrar la transacción, excede el stock actual");
						return new ResponseEntity<JSONObject>(response, HttpStatus.BAD_REQUEST);
					}
				} else {
					response.put("status", HttpStatus.BAD_REQUEST);
					response.put("status_code", "400");
					response.put("message", "No se puede registrar la transacción, el producto no tiene stock");
					return new ResponseEntity<JSONObject>(response, HttpStatus.BAD_REQUEST);
				}
				
			} else {
				response.put("status", HttpStatus.BAD_REQUEST);
				response.put("status_code", "400");
				response.put("message", "No se puede registrar la transacción, el producto no existe");
				return new ResponseEntity<JSONObject>(response, HttpStatus.BAD_REQUEST);
			}
		}
		response.put("status", HttpStatus.BAD_REQUEST);
		response.put("status_code", "400");
		response.put("message", "Tipo de transacción desconocido");
		return new ResponseEntity<JSONObject>(response, HttpStatus.BAD_REQUEST);
	}
}
