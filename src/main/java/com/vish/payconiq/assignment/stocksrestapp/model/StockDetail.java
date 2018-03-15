package com.vish.payconiq.assignment.stocksrestapp.model;

/**
 * @author Tharindu
 * <p>
 * This model class will represent stocks in JSON API Response. 
 * It has the following fields
 * <li>id - (stock id)</li>
 * <li>name - (stock name)</li>
 * <li>currentPrice - (current price of the stock)</li>
 * <li>lastUpdatedTime - (last updated time of the stock)</li>
 * </p>
 */
public class StockDetail {
	
	private Long id;
	private String name;
	private String description;
	private String currentPrice;
	private String lastUpdatedTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

}
