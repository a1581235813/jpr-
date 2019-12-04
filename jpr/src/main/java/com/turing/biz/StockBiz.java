package com.turing.biz;

import java.util.List;
import java.util.Map;

import com.turing.entity.Orders;

public interface StockBiz {

	List<Orders> getNewList(List<Orders> list);

	List<Map<String, String>> toDistinctList(List<Map<String,String>> list);
	List<Map<String, Object>> toDistinctList2(List<Map<String, Object>> list);
	
}
