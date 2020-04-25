package com.wang.service;

import java.util.List;

import com.wang.dao.HomeCostDao;
import com.wang.pojo.HomeCost;

public class HomeCostService {
	
	HomeCostDao homeCostDao = new HomeCostDao();
	
	//新增消费账单
	public int add(HomeCost homecost) {
		return homeCostDao.add(homecost);
	}
	
	//删除消费账单
	public int delete(int id) {
		return homeCostDao.delete(id);
	}
	
	//修改消费账单
	public int update(HomeCost homecost) {
		return homeCostDao.update(homecost);
	}
	
	//关键字查询
	public List<HomeCost> query(String keyword) {
		return homeCostDao.query(keyword);
	}
	
	//全部消费记录
	public List<HomeCost> list() {
		return homeCostDao.list();
	}
	
	//由id查找某条消费记录
	public HomeCost getHomeCostById(int id) {
		return homeCostDao.getHomeCostById(id);
	}
	
}
