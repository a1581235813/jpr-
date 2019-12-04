package com.turing.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
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
import com.turing.entity.IdMapping;
import com.turing.entity.IdMappingExample;
import com.turing.entity.Material;
import com.turing.entity.Orders;
import com.turing.entity.OrdersExample;
import com.turing.entity.OrdersExample.Criteria;
import com.turing.entity.SysUsers;
import com.turing.mapper.IdMappingMapper;
import com.turing.mapper.MaterialMapper;
import com.turing.mapper.OrdersMapper;

@Controller
public class MaterialController {
	@Autowired
	private MaterialMapper mapper;
	@Autowired
	private OrdersMapper Ordersmapper;
	@Autowired
	private IdMappingMapper IdMapping;
	List<Material> list2 = new ArrayList<Material>();

	// 需求计划录入分页
	@GetMapping("/findDSMJson")
	@ResponseBody
	public Map findDSMJson(int page, int rows) {
		PageHelper.startPage(page, rows);
		List<Material> list = mapper.selectByExample(null);
		PageInfo<Material> pageinfo = new PageInfo<Material>(list);
		Map map = new HashMap();
		map.put("total", pageinfo.getTotal());
		map.put("rows", pageinfo.getList());
		return map;

	}

	// 需求计划查看
	@PostMapping("/orderNewform")
	@ResponseBody
	public String orderNewform(@RequestBody List<Material> list, HttpServletRequest Material, HttpServletRequest req) {
		for (Material material2 : list) {
			list2.add(material2);
		}
		req.getSession().setAttribute("materials", list);

		/*
		 * System.out.println(list.size()); req.getSession().setAttribute("materials",
		 * list);
		 */
		return "planman/Order_newform.htm";

	}

	// 需求计划添加
	@PostMapping("/addOrder")
	@ResponseBody()
	public String addOrder(Orders orders, HttpSession session) {
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = df.format(day);

		orders.setOrderNum("100" + date + (int) ((Math.random() * 9 + 1) * 10000));

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date2 = new Date(System.currentTimeMillis());
		SysUsers user = (SysUsers) session.getAttribute("user");
		
		orders.setAuthorId(user.getId().toString());
		orders.setAuthor(user.getLoginId());
		orders.setStartDate(date2);
		Ordersmapper.insertSelective(orders);
		IdMapping id = new IdMapping();
		OrdersExample Example = new OrdersExample();
		Criteria cr = Example.createCriteria();
		cr.andOrderNumEqualTo(orders.getOrderNum());
		List<Orders> or = Ordersmapper.selectByExample(Example);
		id.setOrderId(or.get(0).getId());
		id.setStatus("C001-10");
		IdMapping.insertSelective(id);
		return "success";

	}

	// 需求计划查询加分页
	@GetMapping("/Orderlist")
	@ResponseBody
	public Map Orderlist(int rows, int page, String name, String name2, String state,
			@RequestParam(defaultValue = "1") int st) {
		PageHelper.startPage(page, rows);
		Map map = new HashMap();
		List<Map> list = Ordersmapper.select(name, name2, state, st);

		PageInfo pageinfo = new PageInfo(list);
		map.put("total", pageinfo.getTotal());
		map.put("rows", pageinfo.getList());
		return map;

	}

	// 去需求计划单个查看
	@GetMapping("/queryorders")
	public String queryorders(int id, Model model) {
		model.addAttribute("Orders", Ordersmapper.selectByPrimaryKey((long) id));

		return "planman/print_order_detail.htm";

	}

	// 去修改需求计划
	@GetMapping("/Modifyrequirements")
	public String Modifyrequirements(int id, HttpSession session) {
		session.setAttribute("orderMaterial", Ordersmapper.selectByPrimaryKey((long) id));

		return "planman/update_xuqiujihua.htm";

	}

	// 修改需求计划
	@PostMapping("/udateOrder")
	@ResponseBody
	public String udateOrder(Orders orde) {
		Ordersmapper.updateByPrimaryKeySelective(orde);
		return "success";

	}

	// 删除修改计划
	@PostMapping("/deleteOrder")
	@ResponseBody
	public String deleteOrder(@RequestParam("id[]") int[] id) {
		for (int i : id) {
			IdMappingExample example = new IdMappingExample();
			com.turing.entity.IdMappingExample.Criteria cr = example.createCriteria();
			cr.andOrderIdEqualTo((long) i);
			IdMapping.deleteByExample(example);
			Ordersmapper.deleteByPrimaryKey((long) i);
		}

		return "success";

	}

	// 修改状态
	@PostMapping("/Modifyrequirements")
	@ResponseBody
	public String Modifyrequirements(@RequestParam("id[]") int[] id) {
		for (int i : id) {
			IdMapping ma = new com.turing.entity.IdMapping();
			ma.setStatus("C001-20");
			IdMappingExample example = new IdMappingExample();
			com.turing.entity.IdMappingExample.Criteria cr = example.createCriteria();
			cr.andOrderIdEqualTo((long) i);
			IdMapping.updateByExampleSelective(ma, example);
		}

		return "success";

	}

	// 需求计划查询加分页
	@GetMapping("/Orderlist2")
	@ResponseBody
	public Map Orderlist2(int rows, int page, Orders or, String px) {
		PageHelper.startPage(page, rows);
		Map map = new HashMap();
		OrdersExample example = new OrdersExample();
		Criteria cr = example.createCriteria();
       
		List<Map> list = Ordersmapper.select2(or.getMaterialCode(), or.getMaterialName(), px);

		PageInfo pageinfo = new PageInfo(list);
		map.put("total", pageinfo.getTotal());
		map.put("rows", pageinfo.getList());
		return map;

	}

}
