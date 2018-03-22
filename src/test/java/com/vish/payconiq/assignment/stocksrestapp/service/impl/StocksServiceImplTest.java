package com.vish.payconiq.assignment.stocksrestapp.service.impl;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.vish.payconiq.assignment.stocksrestapp.domain.Stock;
import com.vish.payconiq.assignment.stocksrestapp.domain.StockHistory;
import com.vish.payconiq.assignment.stocksrestapp.exception.ErrorCode;
import com.vish.payconiq.assignment.stocksrestapp.exception.StocksServiceException;
import com.vish.payconiq.assignment.stocksrestapp.model.StockDetail;
import com.vish.payconiq.assignment.stocksrestapp.repository.StockRepository;

public class StocksServiceImplTest {
	
	StocksServiceImpl stockService;
	
	@Mock StockRepository stockRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		stockService = new StocksServiceImpl();
		stockService.setStockRepository(stockRepository);
		
		when(stockRepository.findAll()).thenReturn(getStocksList());
		
		doAnswer(new Answer<Optional<Stock>>() {
			@Override
			public Optional<Stock> answer(InvocationOnMock arg0) throws Throwable {
				Long id = arg0.getArgument(0);
				if (id == 1L) {
					return getStockDetails();
				}
				throw new StocksServiceException("No data available", ErrorCode.NO_DATA_ERROR);
			}
		}).when(stockRepository).findById(any(Long.class));
		
		doAnswer(new Answer<Optional<Stock>>() {
			@Override
			public Optional<Stock> answer(InvocationOnMock arg0) throws Throwable {
				return Optional.empty();
			}
		}).when(stockRepository).findByName(any(String.class));
		
		doAnswer(new Answer<Stock>() {
			@Override
			public Stock answer(InvocationOnMock arg0) throws Throwable {
				Stock stock = arg0.getArgument(0);
				if (stock.getId() == null) {
					stock.setId(10L);
				}
				return stock;
			}
		}).when(stockRepository).save(any(Stock.class));
		
	}
	
	private List<Stock> getStocksList(){
		List<Stock> stocks = new ArrayList<>();
		Stock stock1 = new Stock();
		stock1.setId(1L);
		stock1.setName("VSA");
		stock1.setCurrentPrice(new BigDecimal("110"));
		stock1.setUpdatedTs(new Timestamp(new Date().getTime()));
		
		Stock stock2 = new Stock();
		stock2.setId(2L);
		stock2.setName("FBK");
		stock2.setCurrentPrice(new BigDecimal("120"));
		stock2.setUpdatedTs(new Timestamp(new Date().getTime()));
		
		stocks.add(stock1);
		stocks.add(stock2);
		
		return stocks;
	}
	
	private Optional<Stock> getStockDetails(){
		Stock stock = new Stock();
		stock.setId(1L);
		stock.setName("VSA");
		stock.setCurrentPrice(new BigDecimal("110"));
		Long time = new Date().getTime();
		stock.setUpdatedTs(new Timestamp(time));
		
		List<StockHistory> stockHistoryList = new ArrayList<>();
		
		StockHistory history1 = new StockHistory();
		history1.setId(1L);
		history1.setPriceTs(new Timestamp(time-1000));
		history1.setHistoryPrice(new BigDecimal("99"));
		
		StockHistory history2 = new StockHistory();
		history2.setId(2L);
		history2.setPriceTs(new Timestamp(time-2000));
		history2.setHistoryPrice(new BigDecimal("101"));
		
		stockHistoryList.add(history1);
		stockHistoryList.add(history2);
		
		stock.setStockHistoryList(stockHistoryList);
		
		return Optional.of(stock);
	}
	
	@Test
	public void testGetStocks() throws StocksServiceException {
		List<StockDetail> stockDetails = stockService.getStocks();
		Assert.assertNotNull("Not null list of StockDetail expected",stockDetails);
		Assert.assertEquals("StockDetail list should be of size 2", 2, stockDetails.size());
	}
	
	@Test
	public void testGetStocksDetails() throws StocksServiceException {
		StockDetail stockDetail = stockService.getStockById(1L);
		Assert.assertNotNull("Not null object of StockDetail expected",stockDetail);
		Assert.assertEquals("1 is expected as the price index for StockDetail", 1, stockDetail.getPriceIndex());
		Assert.assertEquals("2 is the size of the history map expected in StockDetail", 2, stockDetail.getPriceHistoryMap().size());
	}
	
	@Test
	public void testAddStock() throws StocksServiceException {
		StockDetail stockDetail = new StockDetail();
		stockDetail.setCurrentPrice("100");
		stockDetail.setName("TST");
		stockDetail.setLastUpdatedTime(new Date());
		stockDetail = stockService.addStock(stockDetail);
		assertNotNull("Not null object expected for StockDetail", stockDetail);
		assertEquals("10 is expected as the id for StockDetail", new Long(10L), stockDetail.getId());
	}
	
	@Test
	public void testUpdateStock() throws StocksServiceException {
		StockDetail stockDetail = new StockDetail();
		stockDetail.setId(1L);
		stockDetail.setCurrentPrice("100");
		stockDetail.setName("TST");
		stockDetail.setLastUpdatedTime(new Date());
		stockDetail = stockService.updateStock(stockDetail);
		assertNotNull("Not null object expected for StockDetail", stockDetail);
		assertEquals("1 is expected as the id for StockDetail", new Long(1L), stockDetail.getId());
	}
	
	
	
}
