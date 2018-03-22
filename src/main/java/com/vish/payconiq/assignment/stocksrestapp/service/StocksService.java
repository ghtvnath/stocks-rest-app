package com.vish.payconiq.assignment.stocksrestapp.service;

import java.util.List;

import com.vish.payconiq.assignment.stocksrestapp.exception.StocksServiceException;
import com.vish.payconiq.assignment.stocksrestapp.model.StockDetail;

public interface StocksService {
	
	List<StockDetail> getStocks() throws StocksServiceException;
	
	StockDetail getStockById(Long stockId) throws StocksServiceException;
	
	StockDetail addStock(StockDetail stockDetail) throws StocksServiceException;
	
	StockDetail updateStock (StockDetail stockDetail) throws StocksServiceException;

	void validateStockDetailRequest(StockDetail stockDetail) throws StocksServiceException;

}
