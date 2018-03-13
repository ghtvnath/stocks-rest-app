package com.vish.payconiq.assignment.stocksrestapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vish.payconiq.assignment.stocksrestapp.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

}
