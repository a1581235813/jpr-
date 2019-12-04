package com.turing.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.turing.biz.StockBiz;
import com.turing.entity.Orders;

@Service
public class StockBizImp implements StockBiz{

	@Override
	public List<Orders> getNewList(List<Orders> list) {
		 HashMap<String, Orders> tempMap = new HashMap<String, Orders>();
	        for (Orders people : list) {
	            String  temp = people.getMaterialName();//获取编号
	            //containsKey(Object key)该方法判断Map集合中是否包含指定的键名，如果包含返回true，不包含返回false
	            //containsValue(Object value)该方法判断Map集合中是否包含指定的键值，如果包含返回true，不包含返回false
	            if (tempMap.containsKey(temp)) {
	            	Orders p = new Orders();
	                p.setMaterialName(temp);
	                p.setId(people.getId());
	                p.setOrderNum(people.getOrderNum());
	                p.setMaterialCode(people.getMaterialCode());
	                p.setMaterialName(people.getMaterialName());
	                
	                //合并相同id的inCome值
	                int  num= Integer.parseInt(tempMap.get(temp).getAmount()) +Integer.parseInt( people.getAmount());
	                p.setAmount(""+num);
	                p.setMeasureUnit(people.getMeasureUnit());
	                p.setUnitPrice(people.getUnitPrice());
	                p.setSumPrice(people.getSumPrice());
	                p.setStartDate(people.getStartDate());
	                p.setEndDate(people.getEndDate());
	                p.setRemark(people.getRemark());
	                p.setAuthorId(people.getAuthorId());
	                p.setAuthor(people.getAuthor());
	                //HashMap不允许key重复，当有key重复时，前面key对应的value值会被覆盖
	                tempMap.put(temp, p);
	            } else {
	                tempMap.put(temp, people);
	            }
	        }
	 
	        //去除重复 编号id 的 list
	        List<Orders> newList = new ArrayList<Orders>();
	        for(String temp:tempMap.keySet()){
	            newList.add(tempMap.get(temp));
	        }
	        return newList;

	
	}
	public List<Map<String, String>> toDistinctList(List<Map<String, String>> list) {
	    
    	Set<String> keysSet = new HashSet<String>();
  
        Iterator<Map<String, String>> it=list.iterator();
        while(it.hasNext()) {
        	Map<String, String> map=it.next();
        	String date = (String) map.get("id");
           
            int beforeSize = keysSet.size();
            keysSet.add(date);
            int afterSize = keysSet.size();
            if(afterSize != (beforeSize + 1)) {
                it.remove();
            }
        }
    return list;
	

}
	@Override
	public List<Map<String, Object>> toDistinctList2(List<Map<String, Object>> list) {
		List<Map<String,Object>> tmpList=new ArrayList<Map<String,Object>>();
		Set<Long> keysSet = new HashSet<Long>();
		for(Map<String, Object> collisionMap : list){
			Long keys = (Long) collisionMap.get("stock_id");
		int beforeSize = keysSet.size();
		keysSet.add(keys);
		int afterSize = keysSet.size();
		if(afterSize == beforeSize + 1){
		tmpList.add(collisionMap);
		}
	
		
		
	}
		return tmpList;
	}
}