package com.todo1.BackEndTodo1.controller;

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

import java.util.Date;
import java.util.List;
import javax.el.MethodNotFoundException;

import com.todo1.BackEndTodo1.models.Products;
import com.todo1.BackEndTodo1.models.Transaction;
import com.todo1.BackEndTodo1.repository.ProductsRepository;
import com.todo1.BackEndTodo1.repository.TransactionRepository;

@RestController
public class ProductsController {
	
	@Autowired
    ProductsRepository productsRepository;
	
	@Autowired
    TransactionRepository transactionRepository;
	
	// OBTENER LOS PRODUCTOS
    @GetMapping("/products")
    public List<Products> getProducts(@RequestParam(name="code",required=false) String code, @RequestParam(name="name",required=false) String name) {
    	try {
    		if(code != null && code != "" && name != null && name != "") {
        		return productsRepository.findByCodeOrNameContainingOrderByNameDesc(code,name);
        	} else if(code != null && code != "") {
        		return productsRepository.findByCodeContainingOrderByCodeDesc(code);
        	} else if(name != null && name != "") {
        		return productsRepository.findByNameContainingOrderByNameDesc(name);
        	} else {
        		return productsRepository.findAll();
        	}
    	} catch (MethodNotFoundException exc) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado", exc);
        }
    }
    
    // CREAR UN PRODUCTO
    @SuppressWarnings("unchecked")
	@PostMapping("/create_product")
    public ResponseEntity<JSONObject> creatProduct(@RequestBody @Validated Products products) {
    	JSONObject response = new JSONObject();
    	if(products.getStock() >= 0) {
    		boolean validation = productsRepository.existsByCode(products.getCode());
    		if(!validation) {
    			Products operation = productsRepository.save(products);
    			response.put("status", HttpStatus.OK);
    			response.put("status_code", "200");
    			response.put("message", "Producto creado");
    			response.put("data", operation);
    			if(products.getStock() > 0) {
    				Transaction transaction = new Transaction();
    				transaction.setType("inputs");
    				transaction.setCode(products.getCode());
    				transaction.setStock(products.getStock());
    				transaction.setDate(new Date());
    				transaction.setPrice(products.getPrice());
    				transactionRepository.save(transaction);
    			}
    			return new ResponseEntity<JSONObject>(response, HttpStatus.OK);
    		} else {
    			response.put("status", HttpStatus.BAD_REQUEST);
    			response.put("status_code", "400");
    			response.put("message", "El producto ya existe");
    			return new ResponseEntity<JSONObject>(response, HttpStatus.BAD_REQUEST);
    		}
    	} else {
    		response.put("status", HttpStatus.BAD_REQUEST);
    		response.put("status_code", "400");
			response.put("message", "El stock inicial no puede ser menor a cero");
    		return new ResponseEntity<JSONObject>(response, HttpStatus.BAD_REQUEST);
    	}
    }
}
