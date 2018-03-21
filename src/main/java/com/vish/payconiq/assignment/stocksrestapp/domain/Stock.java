package com.vish.payconiq.assignment.stocksrestapp.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="T_STOCK")
public class Stock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable=false, length=5, unique=true)
	private String name;
	
	@Column(name="desc", length=30)
	private String description;
	
	@Column(name="current_price")
	private BigDecimal currentPrice;
	
	@Column(name="created_ts")
	private Timestamp createdTs;
	
	@Column(name="updated_ts")
	private Timestamp updatedTs;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "stock")
	@OrderBy("price_ts")
	private List<StockHistory> stockHistoryList;
	
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
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	public Timestamp getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}
	public Timestamp getUpdatedTs() {
		return updatedTs;
	}
	public void setUpdatedTs(Timestamp updatedTs) {
		this.updatedTs = updatedTs;
	}
	
	public List<StockHistory> getStockHistoryList() {
		return stockHistoryList;
	}
	public void setStockHistoryList(List<StockHistory> stockHistoryList) {
		this.stockHistoryList = stockHistoryList;
	}
	
	@PrePersist
	void preInsert() {
	   if (this.createdTs == null) {
		   this.createdTs = new Timestamp(new Date().getTime());
	   }
	   
	   if (this.updatedTs == null) {
		   this.updatedTs = new Timestamp(new Date().getTime());
	   }
	}

}
