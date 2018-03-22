package com.vish.payconiq.assignment.stocksrestapp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.vish.payconiq.assignment.stocksrestapp.domain.Stock;
import com.vish.payconiq.assignment.stocksrestapp.domain.StockHistory;
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
	
	// used only to set mock object in Unit Testing
	protected void setStockRepository(StockRepository repository) {
		this.stocksRepository = repository;
	}
	
	/**
	 * 
	 * @return {@link List} of StockDetail
	 * @throws StocksServiceException
	 * 
	 * <p>
	 * This is the service implementation method for getting list of {@link StockDetail}.
	 * It gets the list of {@link Stock} data from the database and converts to the 
	 * user response form which is {@link StockDetail}
	 * </p>
	 * 
	 */
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
			stockDetails.add(mapStockToStockDetail(stock, true));
		}
		return stockDetails;
	}
	
	/**
	 * 
	 * @param id
	 * @return {@link StockDetail}
	 * @throws StocksServiceException
	 * 
	 * <p>
	 * Provided an id, this service implementation method returns detailed {@link StockDetail}.
	 * It also contains a map of history data in the format of 'date -> price'
	 * </p>
	 */
	@Override
	public StockDetail getStockById(Long stockId) throws StocksServiceException {
		Optional<Stock> stockOpt = stocksRepository.findById(stockId);
		if (!stockOpt.isPresent()){
			// no stock detail available for the provided ID
			throw new StocksServiceException("No stock is available for given stock id", ErrorCode.NO_DATA_ERROR);
		}
		StockDetail stockDetail = mapStockToStockDetail(stockOpt.get(), true);
		return stockDetail;
	}
	
	private StockDetail mapStockToStockDetail(Stock stock, boolean includeHistory) throws StocksServiceException {
		StockDetail stockDetail = new StockDetail();
		stockDetail.setId(stock.getId());
		stockDetail.setName(stock.getName());
		stockDetail.setDescription(stock.getDescription());
		String currentPrice = PriceUtil.formatPrice(stock.getCurrentPrice());
		if (StringUtils.isEmpty(currentPrice)) {
			throw new StocksServiceException("Application error in processing data.", ErrorCode.APPLICATION_ERROR);
		}
		
		stockDetail.setCurrentPrice(currentPrice);
		Date lastUpdatedTime = new Date(stock.getUpdatedTs().getTime());
		stockDetail.setLastUpdatedTime(lastUpdatedTime);
		
		List<StockHistory> stockHistoryList = stock.getStockHistoryList();
		if (CollectionUtils.isEmpty(stockHistoryList)) {
			// no previous prices. Price index is 0
			stockDetail.setPriceIndex(0);
		}else {
			// get the latest price from the price history and compare with the current price
			// set -1 if the price has decreased, 1 if the price has increased, 0 if the price has not changed
			BigDecimal prevPrice = stockHistoryList.get(stockHistoryList.size() -1).getHistoryPrice();
			stockDetail.setPriceIndex(stock.getCurrentPrice().compareTo(prevPrice));
		}

		Map<String, String> priceHistoryMap = new LinkedHashMap<>();
		if (includeHistory) {
			// if price history should be included, prepare price_history map
			List<StockHistory> stocksHistiryList = stock.getStockHistoryList();
			if (!CollectionUtils.isEmpty(stocksHistiryList)) {
				for (StockHistory stockHistory : stocksHistiryList) {
					priceHistoryMap.put(DateUtil.formatDate(stockHistory.getPriceTs()),
							PriceUtil.formatPrice(stockHistory.getHistoryPrice()));
				}
			}
		}
		stockDetail.setPriceHistoryMap(priceHistoryMap);

		return stockDetail;
	}
	
	/**
	 * 
	 * @param stockDetail
	 * @return {@link StockDetail}
	 * @throws StocksServiceException
	 * 
	 * <p>This service implementation method is used for creating a new {@link Stock}.
	 * If a stock already exists with the same name, the existing record will be updated.</p>
	 * 
	 */
	@Override
	public StockDetail addStock(StockDetail stockDetail) throws StocksServiceException{
		
		// if stock already exists by the name, then just update the record. Stock name is Unique.
		Optional<Stock> stockOpt = stocksRepository.findByName(stockDetail.getName().toUpperCase());
		if (stockOpt.isPresent()) {
			stockDetail.setId(stockOpt.get().getId());
			return updateStock(stockDetail);
		}
		
		Stock stock = new Stock();
		stock.setName(stockDetail.getName().toUpperCase());
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
		stockDetail.setId(stock.getId());
		return stockDetail;
	}

	/**
	 * 
	 * @param stockDetail
	 * @return {@link StockDetail}
	 * @throws StocksServiceException
	 * 
	 * <p>This service implementation method is used for updating an existing {@link Stock} record.</p>
	 * 
	 */
	@Override
	public StockDetail updateStock(StockDetail stockDetail) throws StocksServiceException {
		Optional<Stock> stockOpt = stocksRepository.findById(stockDetail.getId());
		if (!stockOpt.isPresent()){
			throw new StocksServiceException("No stock is available for given stock id", ErrorCode.NO_DATA_ERROR);
		}
		
		Stock stock = stockOpt.get();
		// name will not be updated. It is a unique property of stock
		stock.setDescription(stockDetail.getDescription());
		BigDecimal price = PriceUtil.formatToPrice(stockDetail.getCurrentPrice());
		if (price == null){
			throw new StocksServiceException("Invalid parameter for current price", ErrorCode.INPUT_ERROR);
		}
		
		// if the updating price is not equals to current price, put the current price in History table 
		if (stock.getCurrentPrice().compareTo(price) != 0) {
			StockHistory newHistory = new StockHistory();
			newHistory.setHistoryPrice(stock.getCurrentPrice());
			newHistory.setPriceTs(stock.getUpdatedTs());
			newHistory.setStock(stock);
			stock.getStockHistoryList().add(newHistory);
			stock.setCurrentPrice(price);
		}
		
		stock = stocksRepository.save(stock);
		if (stock == null){
			throw new StocksServiceException("Error persisting stock data", ErrorCode.APPLICATION_ERROR);
		}
		return stockDetail;
	}

}
