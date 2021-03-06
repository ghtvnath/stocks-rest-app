package com.vish.payconiq.assignment.stocksrestapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vish.payconiq.assignment.stocksrestapp.domain.Stock;
import com.vish.payconiq.assignment.stocksrestapp.exception.StocksServiceException;
import com.vish.payconiq.assignment.stocksrestapp.model.ErrorResponse;
import com.vish.payconiq.assignment.stocksrestapp.model.StockDetail;
import com.vish.payconiq.assignment.stocksrestapp.service.StocksService;

/**
 * @author Tharindu
 * 
 * <p>This controller is responsible for handling HTTP Requests for Stocks and 
 * serving data as JSON</p>
 */
@RestController
@RequestMapping("api/stocks")
public class StocksController {
	
	@Autowired
	StocksService stocksService;
	
	/**
	 * In application runtime, stocksService is autowired by spring framework. 
	 * 
	 * This setter method is only for JUnit to set Mock service. 
	 * By declaring access method 'protected', it is made sure that this setter method 
	 * is not being called from any other part of the application
	 */
	protected void setStocksService(StocksService stocksService){
		this.stocksService = stocksService;
	}
	
	/**
	 * 
	 * @return {@link List} of StockDetail
	 * @throws StocksServiceException
	 * 
	 * <p>
	 * This GET Http Request method returns list of all {@link StockDetail} available.
	 * </p>
	 * 
	 */
	@GetMapping
	@ResponseBody
	public List<StockDetail> getListOfStocks() throws StocksServiceException{
		List<StockDetail> stockDetailsList = stocksService.getStocks();
		return stockDetailsList;
	}
	
	/**
	 * 
	 * @param id
	 * @return {@link StockDetail}
	 * @throws StocksServiceException
	 * 
	 * <p>
	 * Provided an id, this HTTP Get Request method returns detailed {@link StockDetail}
	 * It also contains a map of history data in the format of 'date -> price'
	 * </p>
	 */
	@GetMapping("/{id}")
	@ResponseBody
	public StockDetail getStockById(@PathVariable Long id) throws StocksServiceException{
		StockDetail stockDetail = stocksService.getStockById(id); 
		return stockDetail;
	}
	
	/**
	 * 
	 * @param stockDetail
	 * @return {@link StockDetail}
	 * @throws StocksServiceException
	 * 
	 * <p>This HTTP Post method is used for creating a new {@link Stock}.
	 * If a stock already exists with the same name, the existing record will be updated.</p>
	 * 
	 */
	@PostMapping
	public StockDetail addStock(@RequestBody StockDetail stockDetail) throws StocksServiceException {
		stockDetail = stocksService.addStock(stockDetail);
		return stockDetail;
	}
	
	/**
	 * 
	 * @param stockDetail
	 * @return {@link StockDetail}
	 * @throws StocksServiceException
	 * 
	 * <p>This HTTP Put method is used for updating an existing {@link Stock} record.</p>
	 * 
	 */
	@PutMapping
	public StockDetail updateStock(@RequestBody StockDetail stockDetail) throws StocksServiceException {
		stockDetail = stocksService.updateStock(stockDetail);
		return stockDetail;
	}
	
	/**
	 * 
	 * @param ex
	 * @return ResponseEntity with error message and HttpStatus code. 
	 * 
	 * <p>This exception handler method handles the checked exceptions thrown by the MVC Controller</p>
	 */
	@ExceptionHandler({ StocksServiceException.class })
	public ResponseEntity<ErrorResponse> handleException(StocksServiceException ex) {
		HttpStatus status = null;
		switch (ex.getCode()) {
		case INPUT_ERROR:
			status = HttpStatus.BAD_REQUEST;
			break;

		case NO_DATA_ERROR:
			status = HttpStatus.NO_CONTENT;

		case APPLICATION_ERROR:
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			break;
		default:
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			break;
		}
		ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<ErrorResponse>(
				new ErrorResponse(ex.getCode().getDescription(), ex.getMessage()), status);
		return responseEntity;
	}
	

}
