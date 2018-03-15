package com.vish.payconiq.assignment.stocksrestapp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vish.payconiq.assignment.stocksrestapp.entity.Stock;
import com.vish.payconiq.assignment.stocksrestapp.exception.ErrorCode;
import com.vish.payconiq.assignment.stocksrestapp.exception.StocksServiceException;
import com.vish.payconiq.assignment.stocksrestapp.model.StockDetail;
import com.vish.payconiq.assignment.stocksrestapp.repository.StockRepository;
import com.vish.payconiq.assignment.stocksrestapp.service.StocksService;
import com.vish.payconiq.assignment.stocksrestapp.util.DateUtil;
import com.vish.payconiq.assignment.stocksrestapp.util.PriceUtil;

@Service
public class StocksServiceImpl implements StocksService {

	@Autowired
	StockRepository stocksRepository;
	
	@Override
	public List<StockDetail> getStocks() throws StocksServiceException {
		List<Stock> stocks;
		try {
			stocks = stocksRepository.findAll();
		} catch (Exception e) {
			throw new StocksServiceException("Error connecting to database.", e, ErrorCode.APPLICATION_ERROR);
		}
		
		if (CollectionUtils.isEmpty(stocks)){
			// No stock details available in the database
			throw new StocksServiceException("No data available for stocks", ErrorCode.NO_DATA_ERROR);
		}
		
		List<StockDetail> stockDetails = new ArrayList<>();
		
		for (Stock stock : stocks){
			stockDetails.add(mapStockToStockDetail(stock));
		}
		return stockDetails;
	}
	
	@Override
	public StockDetail getStockById(Long stockId) throws StocksServiceException {
		Optional<Stock> stockOpt = stocksRepository.findById(stockId);
		if (!stockOpt.isPresent()){
			throw new StocksServiceException("No stock is available for given stock id", ErrorCode.NO_DATA_ERROR);
		}
		StockDetail stockDetail = mapStockToStockDetail(stockOpt.get());
		return stockDetail;
	}
	
	private StockDetail mapStockToStockDetail (Stock stock){
		StockDetail stockDetail = new StockDetail();
		stockDetail.setName(stock.getName());
		String currentPrice = PriceUtil.formatPrice(stock.getCurrentPrice());
		stockDetail.setCurrentPrice(currentPrice);
		String lastUpdatedTime = DateUtil.formatDate(stock.getUpdatedTs());
		stockDetail.setLastUpdatedTime(lastUpdatedTime);
		return stockDetail;
	}
	
	@Override
	public StockDetail addStock(StockDetail stockDetail) throws StocksServiceException{
		Stock stock = new Stock();
		stock.setName(stockDetail.getName());
		stock.setDescription(stockDetail.getDescription());
		BigDecimal price = PriceUtil.formatToPrice(stockDetail.getCurrentPrice());
		if (price == null){
			throw new StocksServiceException("Invalid parameter for current price", ErrorCode.INPUT_ERROR);
		}
		stock.setCurrentPrice(price);
		stock = stocksRepository.save(stock);
		if (stock == null){
			throw new StocksServiceException("Error persisting stock data", ErrorCode.APPLICATION_ERROR);
		}
		return stockDetail;
	}

	@Override
	public StockDetail updateStock(StockDetail stockDetail) throws StocksServiceException {
		Optional<Stock> stockOpt = stocksRepository.findById(stockDetail.getId());
		if (!stockOpt.isPresent()){
			throw new StocksServiceException("No stock is available for given stock id", ErrorCode.NO_DATA_ERROR);
		}
		Stock stock = stockOpt.get();
		stock.setName(stockDetail.getName());
		stock.setDescription(stockDetail.getDescription());
		BigDecimal price = PriceUtil.formatToPrice(stockDetail.getCurrentPrice());
		if (price == null){
			throw new StocksServiceException("Invalid parameter for current price", ErrorCode.INPUT_ERROR);
		}
		stock.setCurrentPrice(price);
		stock = stocksRepository.save(stock);
		if (stock == null){
			throw new StocksServiceException("Error persisting stock data", ErrorCode.APPLICATION_ERROR);
		}
		return stockDetail;
	}

}
