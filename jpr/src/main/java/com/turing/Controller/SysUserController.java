package com.turing.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.resource.HttpResource;

import com.turing.biz.SysusersBiz;
import com.turing.entity.SysUsers;

@Controller
public class SysUserController {
    @Autowired
	SysusersBiz biz;
    @PostMapping("/loginuser")
    public String login(SysUsers user,HttpServletRequest request) {
    SysUsers user2= biz.login(user);
       if(user2!=null) {
    	   request.getSession().setAttribute("user", user2);
    	   return "/index";
       }else {
    	   return "/login";
       }
		
    	
    }
}
