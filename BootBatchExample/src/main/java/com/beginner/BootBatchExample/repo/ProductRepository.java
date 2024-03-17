package com.beginner.BootBatchExample.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beginner.BootBatchExample.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
