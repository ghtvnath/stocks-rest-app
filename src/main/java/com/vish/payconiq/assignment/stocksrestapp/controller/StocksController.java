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
	
	@GetMapping
	@ResponseBody
	public List<StockDetail> getListOfStocks() throws StocksServiceException{
		List<StockDetail> stockDetailsList = stocksService.getStocks();
		return stockDetailsList;
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public StockDetail getStockById(@PathVariable Long id) throws StocksServiceException{
		StockDetail stockDetail = stocksService.getStockById(id); 
		return stockDetail;
	}
	
	@PostMapping
	public StockDetail addStock(@RequestBody StockDetail stockDetail) throws StocksServiceException {
		stockDetail = stocksService.addStock(stockDetail);
		return stockDetail;
	}
	
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
