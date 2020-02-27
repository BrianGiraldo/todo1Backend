package com.todo1.BackEndTodo1.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "todo1_transactions")
public class Transaction {
	
	@Id
    @GeneratedValue
    private Integer id;
	
	@Column(name="transaction_type")
	@NotEmpty(message="El tipo de transaccion es obligatorio")
	private String type;
	
	@Column(name="transaction_producto_code")
	@NotEmpty(message="El codigo del producto es obligatorio")
	private String code;
	
	@Column(name="transaction_producto_stock", nullable = false)
	@Min(1)
	@NotNull(message="La cantidad del producto es obligatorio")
	private Integer stock;
	
	@Column(name="transaction_unitary_price", nullable = false)
	@Min(1)
	@NotNull(message="El precio del producto es obligatorio")
	private Integer price;
	
	@Column(name="transaction_date")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	public Integer getId() {
        return id;
    }
	
	public void setId(Integer id) {
        this.id = id;
    }
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
        this.type = type;
    }
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
        this.code = code;
    }
	
	public Integer getStock() {
		return stock;
	}
	
	public void setStock(Integer stock) {
        this.stock = stock;
    }
	
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
        this.price = price;
    }
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date datetime) {
        this.date = datetime;
    }

}
