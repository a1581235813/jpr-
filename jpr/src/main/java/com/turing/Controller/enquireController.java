package com.turing.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.turing.biz.StockBiz;
import com.turing.entity.Orders;
import com.turing.mapper.EnquireMapper;
import com.turing.mapper.OrdersMapper;
import com.turing.mapper.StockMapper;

@Controller
public class enquireController {
     @Autowired
	  EnquireMapper enquiremapper;
     @Autowired
     StockMapper  stockmapper;
     @Autowired
     StockBiz biz;
     @Autowired
     OrdersMapper orderMapper;
     @PostMapping("/Enquirefindall")
     @ResponseBody
     public Map findall(int page,int rows,String name,String type) {
    	 PageHelper.startPage(page, rows);
         List<Map> List = stockmapper.findallStock4(name,type);
         PageInfo pageinfo=new PageInfo(List);
          List list2= biz.toDistinctList2(pageinfo.getList());
         Map map=new HashMap();
         map.put("total", list2.size()+1);
     	map.put("rows", list2);
		return map;
    	 
     }
     //去编制询价书
     @GetMapping("/addenquire")
     public String findall2(Model mode,long id) {
    	List<Map<String, Object>> list=stockmapper.idStock(id);
    	mode.addAttribute("orders", list);
		return "queryandqueto/Enquire_bianzhi.htm";
    	 
     }
}
