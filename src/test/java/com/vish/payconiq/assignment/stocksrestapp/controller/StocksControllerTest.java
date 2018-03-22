package com.vish.payconiq.assignment.stocksrestapp.controller;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.vish.payconiq.assignment.stocksrestapp.exception.ErrorCode;
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
		
		doAnswer(new Answer<StockDetail>() {
			@Override
			public StockDetail answer(InvocationOnMock arg0) throws Throwable {
				Long id = arg0.getArgument(0);
				if (id == 1) {
					return getStockDetails();
				}
				throw new StocksServiceException("No data error", ErrorCode.NO_DATA_ERROR);
			}
		}).when(stockService).getStockById(any(Long.class));
		
		doAnswer(new Answer<StockDetail>() {
			@Override
			public StockDetail answer(InvocationOnMock arg0) throws Throwable {
				StockDetail stockDetail = arg0.getArgument(0);
				if (!StringUtils.isEmpty(stockDetail.getName()) && !StringUtils.isEmpty(stockDetail.getCurrentPrice())) {
					stockDetail.setId(10L);
					return stockDetail;
				}
				throw new StocksServiceException("Input Error", ErrorCode.INPUT_ERROR);
			}
		}).when(stockService).addStock(any(StockDetail.class));
		
		doAnswer(new Answer<StockDetail>() {
			@Override
			public StockDetail answer(InvocationOnMock arg0) throws Throwable {
				StockDetail stockDetail = arg0.getArgument(0);
				if (stockDetail.getId() != null && !StringUtils.isEmpty(stockDetail.getName()) && !StringUtils.isEmpty(stockDetail.getCurrentPrice())) {
					return stockDetail;
				}
				throw new StocksServiceException("Input Error", ErrorCode.INPUT_ERROR);
			}
		}).when(stockService).updateStock(any(StockDetail.class));
		
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
	
	private StockDetail getStockDetails() {
		StockDetail stockDetail = new StockDetail();
		stockDetail.setId(1L);
		return stockDetail;
	}
	
	@Test
	public void testGetListOfStocks() throws StocksServiceException {
		List<StockDetail> stockDetails = controller.getListOfStocks();
		Assert.assertTrue("Non empty list of type StockDetail is expected.", !CollectionUtils.isEmpty(stockDetails));
	}
	
	@Test 
	public void testGetStockById() throws StocksServiceException {
		StockDetail stockDetail = controller.getStockById(1L);
		Assert.assertTrue("Non null StockDetail is expected", stockDetail != null);
		Assert.assertEquals("1 is expected as the id of the StockDetail", new Long(1L), stockDetail.getId());
	}
	
	@Test 
	public void testAddStock() throws Exception {
		StockDetail stockDetail = new StockDetail();
		stockDetail.setCurrentPrice("100");
		stockDetail.setName("TST");
		stockDetail.setLastUpdatedTime(new Date());
		stockDetail = controller.addStock(stockDetail);
		
		Assert.assertTrue("Non null StockDetail is expected", stockDetail != null);
		Assert.assertEquals("1 is expected as the id of the StockDetail", new Long(10L), stockDetail.getId());
	}
	
	@Test 
	public void testUpdateStock() throws Exception {
		StockDetail stockDetail = new StockDetail();
		stockDetail.setId(10L);
		stockDetail.setCurrentPrice("100");
		stockDetail.setName("TST");
		stockDetail.setLastUpdatedTime(new Date());
		stockDetail = controller.updateStock(stockDetail);
		
		Assert.assertTrue("Non null StockDetail is expected", stockDetail != null);
		Assert.assertEquals("1 is expected as the id of the StockDetail", new Long(10L), stockDetail.getId());
	}

}
