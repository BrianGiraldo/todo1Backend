package com.todo1.BackEndTodo1.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "todo1_products")
public class Products {
	@Id
    @GeneratedValue
    private Integer id;
	
	@Column(name="products_name")
	@NotEmpty(message="El nombre del producto es obligatorio")
	private String name;
	
	@Column(name="products_code",unique=true)
	@NotEmpty(message="El código del producto es obligatorio")
	private String code;
	
	@Column(name="products_stock",columnDefinition = "int default 0")
	@NotNull(message="La cantidad del producto es obligatorio")
	private Integer stock;
	
	@Column(name="products_price",columnDefinition = "int default 0")
	private Integer price;
	
	public Integer getId() {
        return id;
    }
	
	public void setId(Integer id) {
        this.id = id;
    }
	
	public String getName() {
		return name;
	}
	
	public void setName(String products_name) {
        this.name = products_name;
    }
	
	public String getCode() {
        return code;
    }
	
	public void setCode(String products_code) {
        this.code = products_code;
    }
	
	public Integer getStock() {
        return stock;
    }
	
	public void setStock(Integer products_stock) {
        this.stock = products_stock;
    }
	
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
        this.price = price;
    }
}
