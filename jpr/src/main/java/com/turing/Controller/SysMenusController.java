package com.turing.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.turing.entity.SysMenus;
import com.turing.mapper.SysMenusMapper;
import com.turing.util.TreeUtil;


@Controller
public class SysMenusController {
    @Autowired      
	SysMenusMapper mapper;
    @GetMapping("/sysmenusjson")
    @ResponseBody
     public List<SysMenus> tree(){
    	 List<SysMenus> list= mapper.selectByExample(null);
    	
		return TreeUtil.findParent(list) ;
    	 
     }
   
  
}
