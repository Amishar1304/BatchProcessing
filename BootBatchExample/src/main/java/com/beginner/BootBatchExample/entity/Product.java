package com.beginner.BootBatchExample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
//@Table(name ="products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int productId;
	private String title;
	private String description;
	private String price;
	private String discount;

	public Product() {
		super();
	}

	public Product(int id, int productId, String title, String description, String price, String discount) {
		super();
		this.id = id;
		this.productId = productId;
		this.title = title;
		this.description = description;
		this.price = price;
		this.discount = discount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productId=" + productId + ", title=" + title + ", description=" + description
				+ ", price=" + price + ", discount=" + discount + "]";
	}

}
