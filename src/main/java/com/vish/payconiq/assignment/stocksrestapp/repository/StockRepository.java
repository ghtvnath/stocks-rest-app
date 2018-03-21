package com.vish.payconiq.assignment.stocksrestapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vish.payconiq.assignment.stocksrestapp.domain.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
	
	Optional<Stock> findByName(final String name);

}
