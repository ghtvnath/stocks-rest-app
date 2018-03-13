package com.vish.payconiq.assignment.stocksrestapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vish.payconiq.assignment.stocksrestapp.entity.Stock;
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
	public List<StockDetail> getStocks() {
		List<Stock> stocks = stocksRepository.findAll();
		List<StockDetail> stockDetails = new ArrayList<>();
		
		for (Stock stock : stocks){
			stockDetails.add(mapStockToStockDetail(stock));
		}
		return stockDetails;
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

}
