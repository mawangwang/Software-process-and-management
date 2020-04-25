package com.wang.pojo;

import java.math.BigDecimal;

public class HomeCost {
	
	private int id;//id
    private String name;//消费名称
    private BigDecimal money;//消费金额
    private String date;//消费日期
    private BigDecimal sum;//累计消费
    private String ip;
    
	public HomeCost(String name, BigDecimal money, String ip) {
		super();
		this.name = name;
		this.money = money;
		this.ip = ip;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getMoney() {
        return money;
    }
    public void setMoney(BigDecimal money) {
        this.money = money;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	
	@Override
	public String toString() {
		return "HomeCost [id=" + id + ", name=" + name + ", money=" + money + ", date=" + date + ", sum=" + sum
				+ ", ip=" + ip + "]";
	}
	
	public HomeCost() {}
    
    public HomeCost(String name, BigDecimal money) {
		super();
		this.name = name;
		this.money = money;
	}
    
    public HomeCost(int id,String name,BigDecimal money, String date) {
    	super();
        this.id=id;
        this.name = name;
        this.money=money;
        this.date=date;
    }
	public HomeCost(int id, String name, BigDecimal money, String date, BigDecimal sum) {
		super();
		this.id = id;
		this.name = name;
		this.money = money;
		this.date = date;
		this.sum = sum;
	}
	
}
