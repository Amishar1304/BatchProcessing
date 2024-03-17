package com.beginner.BootBatchExample.config;

import org.springframework.batch.item.ItemProcessor;

import com.beginner.BootBatchExample.entity.Product;

import lombok.Data;

@Data
public class CustomItemProcessor implements ItemProcessor<Product, Product> {

	@Override
	public Product process(Product item) throws Exception {

		return item;
	}

}
