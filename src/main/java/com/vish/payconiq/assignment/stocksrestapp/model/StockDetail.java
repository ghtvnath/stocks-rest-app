package com.vish.payconiq.assignment.stocksrestapp.model;

import java.util.Date;
import java.util.Map;

/**
 * @author Tharindu
 * <p>
 * This model class will represent stocks in JSON API Response. 
 * It has the following fields
 * <li>id - (stock id)</li>
 * <li>name - (stock name)</li>
 * <li>currentPrice - (current price of the stock)</li>
 * <li>lastUpdatedTime - (last updated time of the stock)</li>
 * <li>priceIndex - (Whether price is a new one [0], increased [1], or decreased [-1] )</li>
 * <li>priceHistoryMap - (History of prices for the particular stock. This will be included 
 * when the details for a particular stock is enquired, not for the list of stocks)</li>
 * </p>
 */
public class StockDetail {
	
	private Long id;
	private String name;
	private String description;
	private String currentPrice;
	private Date lastUpdatedTime;
	private int priceIndex; // 0 for new, -1 for decreases, 1 for increased
	private Map<String, String> priceHistoryMap;
	
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
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	public Map<String, String> getPriceHistoryMap() {
		return priceHistoryMap;
	}
	public void setPriceHistoryMap(Map<String, String> priceHistoryMap) {
		this.priceHistoryMap = priceHistoryMap;
	}
	
	public int getPriceIndex() {
		return priceIndex;
	}
	public void setPriceIndex(int priceIndex) {
		this.priceIndex = priceIndex;
	}
	

}
