package com.vish.payconiq.assignment.stocksrestapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	public List<StockDetail> getListOfStocks(){
		List<StockDetail> stockDetails = stocksService.getStocks();
		return stockDetails;
	}

}
