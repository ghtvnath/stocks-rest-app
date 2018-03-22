package com.vish.payconiq.assignment.stocksrestapp.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.CollectionUtils;

import com.vish.payconiq.assignment.stocksrestapp.exception.StocksServiceException;
import com.vish.payconiq.assignment.stocksrestapp.model.StockDetail;
import com.vish.payconiq.assignment.stocksrestapp.service.StocksService;


public class StocksControllerTest {
	
	StocksController controller;
	
	@Mock StocksService stockService;
	
	@Before
	public void setUp() throws StocksServiceException{
		MockitoAnnotations.initMocks(this);
		controller = new StocksController();
		controller.setStocksService(stockService);
		
		when(stockService.getStocks()).thenReturn(getTestStocksList());
	}
	
	private List<StockDetail> getTestStocksList(){
		List<StockDetail> stocksList = new ArrayList<>();
		
		StockDetail stockDetail1 = new StockDetail();
		StockDetail stockDetail2 = new StockDetail();
		// list of stockDetails is enough for Unit Testing purposes. 
		
		stocksList.add(stockDetail1);
		stocksList.add(stockDetail2);
		
		return stocksList;
	}
	
	@Test
	public void testGetListOfStocks() throws StocksServiceException {
		List<StockDetail> stockDetails = controller.getListOfStocks();
		Assert.assertTrue("Non empty list of type StockDetail is expected.", !CollectionUtils.isEmpty(stockDetails));
	}

}
