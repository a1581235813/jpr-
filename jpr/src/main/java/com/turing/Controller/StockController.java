package com.turing.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.turing.biz.StockBiz;
import com.turing.entity.IdMapping;
import com.turing.entity.IdMappingExample;
import com.turing.entity.Orders;
import com.turing.entity.Stock;
import com.turing.entity.StockExample;
import com.turing.entity.StockSupplier;
import com.turing.entity.SuppMaterialExample;
import com.turing.entity.Supplier;
import com.turing.entity.SupplierExample;
import com.turing.mapper.IdMappingMapper;
import com.turing.mapper.OrdersMapper;
import com.turing.mapper.StockMapper;
import com.turing.mapper.StockSupplierMapper;
import com.turing.mapper.SupplierMapper;

@Controller
public class StockController {
    @Autowired
	OrdersMapper ordersMapper;
    @Autowired
    StockBiz biz;
    @Autowired
    SupplierMapper suppmapper;
    @Autowired
    IdMappingMapper idmapper;
    @GetMapping("/bianzhicaigoujihualist")
    public String ordersList( int [] ids,Model mode,HttpSession session) {
    	List list=new ArrayList<>();
    	for (int i : ids) {
		 Orders or=ordersMapper.selectByPrimaryKey((long)i);
		 list.add(or);
		}
    	List<Orders> list2= biz.getNewList(list);
    	
    	List<Map>  listmap=suppmapper.findall(list2.get(0).getMaterialName());
        mode.addAttribute("Supplier", listmap);
        session.setAttribute("Orderslist", list2);
      
        session.setAttribute("Orderslist2", list);

		return "planman/bianzhicaigoujihua.html";
    
    }
    @GetMapping("/bianzhicaigoujihualist2")
    public String ordersList2(Model mode,String name) {
    	
    	List<Map>  listmap=suppmapper.findall(name+"\r\n" + 
    			"	");
    	mode.addAttribute("Supplier", listmap);
    	return "planman/bianzhicaigoujihua.html";
    	
    }
    @Autowired
   private  StockMapper StockMapper;
    @Autowired
    private StockSupplierMapper StockSupplierMapper;
    @PostMapping("/addStock")
    @ResponseBody
    //根据定向询价还是公开求购添加
    public void addorders(Model mode,Stock st,@RequestParam("ids[]") int [] ids,@RequestParam(defaultValue="1") int id2) {
    	System.out.println(st.getStockType());
    	  if(st.getStockType().equals("C000-1")) {
    		 
    			  Date day = new Date();
    				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    				String date = df.format(day);
                   String num="200" + date + (int)((Math.random() * 9 + 1) * 10000);
    			 st.setStockNum(num);
    			  StockMapper.insertSelective(st);
    			  StockExample example=new StockExample();
    			  example.createCriteria().andStockNumEqualTo(num);
    			  List<Stock> list=	StockMapper.selectByExample(example);
    			  for (int i : ids) {
    				  IdMapping ma=new IdMapping();
        			  ma.setStatus("C001-30");
        			  ma.setStockId(list.get(0).getId());
        			  IdMappingExample example2=new IdMappingExample();
        			  example2.createCriteria().andOrderIdEqualTo((long)i);
        			  idmapper.updateByExampleSelective(ma, example2);
				}
    			  
    			  
    			  
			
    	  }else {
    		  System.out.println("id:"+st.getId());
    		  Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String date = df.format(day);
             String num="200" + date + (int)((Math.random() * 9 + 1) * 10000);
			 st.setStockNum(num);
			  StockMapper.insertSelective(st);
			  StockExample example=new StockExample();
			  example.createCriteria().andStockNumEqualTo(num);
			  List<Stock> list=	StockMapper.selectByExample(example);
			  StockSupplier stock=new StockSupplier();
			  stock.setStockId(list.get(0).getId());
			  stock.setSupplierId((long)id2);
			  StockSupplierMapper.insertSelective(stock);
			  for (int i : ids) {
				  IdMapping ma=new IdMapping();
  			  ma.setStatus("C001-30");
  			  ma.setStockId(list.get(0).getId());
  			  IdMappingExample example2=new IdMappingExample();
  			  example2.createCriteria().andOrderIdEqualTo((long)i);
  			  idmapper.updateByExampleSelective(ma, example2);
			}
    		  
    	  }
    	
    }
    //查询分页
    @GetMapping("/selectstock2")
    @ResponseBody
    public Map findall2(int page,int rows,String name1,String state) {
    	  PageHelper.startPage(page, rows);
    	List<Map<String, Object>> list=  StockMapper.findall(name1,state);
    	PageInfo pageInfo=new PageInfo(list);
    	  HashMap map=new HashMap ();
    	 List<Map<String ,Object>>  list2= biz.toDistinctList2(pageInfo.getList());
    	 map.put("total", list2.size()+1);
 		map.put("rows", list2);
    			return map  ;
    	
    }
    //根据id查询信息
    @GetMapping("idstock")
   public String  idstock(long id,Model model) {
     List<Map<String ,Object>> 	list =StockMapper.idStock(id);
     model.addAttribute("stocks", list);
     List<Map<String ,Object>>  list2= biz.toDistinctList2(list);
    for (int i = 0; i < list2.size(); i++) {
		Map<String,Object> map=list2.get(i);
	    model.addAttribute("name", map.get("stock_type"));
	}
    
     model.addAttribute("stocks2", list2);
	return "planman/xjfatz_xjfamx.htm";
	   
   }
    //修改状态为未审批
     @PostMapping("/updatestock")
     @ResponseBody
    public String updatestock(int id) {
    	 IdMapping mapp=new IdMapping();
    	 mapp.setStatus("C001-40");
    	IdMappingExample example=new IdMappingExample();
    	example.createCriteria().andStockIdEqualTo((long)id);
    	idmapper.updateByExampleSelective(mapp, example);
		return "success";
    	
    }
     //查询全部采购计划
     @GetMapping("/findallStock")
     @ResponseBody
     public Map findallStock(int page,int rows) {
    	 PageHelper.startPage(page, rows);
    	
    	   PageInfo pageinfo=new PageInfo(StockMapper.findallStock());
    	   List  list=biz.toDistinctList2(pageinfo.getList());
    	   Map map=new HashMap();
    	   map.put("total", list.size()+1);
    		map.put("rows", list);
		return map;
    	 
     }
     //去审批页面
     @GetMapping("/Apply_caiwushenpi")
     public String findidStock(Model model,int id) {
    	 model.addAttribute("id",id);
		return "contractmanager/Apply_caiwushenpi.htm";
    	 
     }
     //修改状态为待下达 
     @GetMapping("/updatestatus")
     public String updatestatus(int id,String sta) {
    	 IdMapping map=new IdMapping();
    	 map.setStatus(sta);
    	 IdMappingExample example=new IdMappingExample();
    	 example.createCriteria().andStockIdEqualTo((long)id);
    	 idmapper.updateByExampleSelective(map, example);
    	 
		return "contractmanager/Apply_daishencaiwu2.htm";
    	 
     }
     //去采购计划下达查询所有采购计划
     @GetMapping("selectstock3")
     @ResponseBody
     public Map findall3(int page,int rows) {
   	  PageHelper.startPage(page, rows);
   	List<Map> list=  StockMapper.findallStock2();
   	PageInfo pageInfo=new PageInfo(list);
   	  HashMap map=new HashMap ();
   	 List<Map<String ,Object>>  list2= biz.toDistinctList2(pageInfo.getList());
   	 map.put("total", list2.size()+1);
		map.put("rows", list2);
   			return map  ;
   	
   }
     //修改状态为已下达
     @PostMapping("/updatestock2")
     @ResponseBody
     public String updatestock2(int id) {
    	 IdMapping map=new IdMapping();
    	 map.setStatus("C001-60");
    	 IdMappingExample example=new IdMappingExample();
    	 example.createCriteria().andStockIdEqualTo((long)id);
    	 idmapper.updateByExampleSelective(map, example);
		return "success";
    	 
     }
     //查询驳回的采购计划
     @GetMapping("/selectstock4")
     @ResponseBody
     public Map findall4(int page,int rows) {
	 PageHelper.startPage(page, rows);
	 List<Map> list=  StockMapper.findallStock3();
	 PageInfo pageInfo=new PageInfo(list);
	HashMap map=new HashMap ();
	 List<Map<String ,Object>>  list2= biz.toDistinctList2(pageInfo.getList());
	  map.put("total", list2.size()+1);
	map.put("rows", list2);
	   	return map  ;
	
    	 
     }
     //详细查询驳回采购计划
     @GetMapping("/idorders")
     public String idorders(long id ,Model model) {
    	 System.out.println(id);
    	 List<Map<String ,Object>> 	list =StockMapper.idStock(id);
    	 
         model.addAttribute("stocks", list);
         List<Map<String ,Object>>  list2= biz.toDistinctList2(list);
        for (int i = 0; i < list2.size(); i++) {
    		Map<String,Object> map=list2.get(i);
    	    model.addAttribute("name", map.get("stock_type"));
    	}
        
         model.addAttribute("stocks2", list2);
    	return "planman/xjfatz_xjfamx2.htm";
    	 
     }
     //查询供应商和采购计划 需求计划
     @GetMapping("/xjfatz_xjfamx3")
     public String xjfatz_xjfamx3(long id,long id2,HttpSession session,Model model) {
        List<Map<String ,Object>> 	list =StockMapper.idStock(id);
    	 
             session.setAttribute("stocks", list);
         List<Map<String ,Object>>  list2= biz.toDistinctList2(list);
        for (int i = 0; i < list2.size(); i++) {
    		Map<String,Object> map=list2.get(i);
    		session.setAttribute("name", map.get("stock_type"));
    	}
        
        session.setAttribute("stocks2", list2);
        
        List<Map> list21=idmapper.idorder(id2);
        
        
        List<Map>  listmap=suppmapper.findall((String)list21.get(0).get("material_name"));
        model.addAttribute("listmap", listmap);
		return "planman/xjfatz_xjfamx3.htm";
    	 
     }
     @PostMapping("/idcompany")
     @ResponseBody
     public List idcompany(String name) {
    	 
    	  
    	  List<Map>  list=suppmapper.findall(name);
		return list;
    	 
     }

}
