package com.turing.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webconfig implements WebMvcConfigurer {
     
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/index").setViewName("index.html");
		registry.addViewController("/bar").setViewName("bar.html");
		registry.addViewController("/leftRequire").setViewName("leftRequire.html");
		registry.addViewController("/mainRequire").setViewName("mainRequire.html");
		registry.addViewController("/tree").setViewName("debris/tree.html");
		registry.addViewController("/login").setViewName("login.html");
		registry.addViewController("/pclass_select").setViewName("planman/pclass_select.html");
		registry.addViewController("/Order").setViewName("planman/Order_newform.htm");
		registry.addViewController("/Order_ytb_list").setViewName("planman/Order_ytb_list2.htm");
		registry.addViewController("/Order_wbxjfa_list2").setViewName("planman/Order_wbxjfa_list2.htm");
		registry.addViewController("/Project_list").setViewName("planman/Project_list1_1.htm");
		registry.addViewController("/Apply_daishencaiwu").setViewName("contractmanager/Apply_daishencaiwu2.htm");
		registry.addViewController("/Project_list4_list").setViewName("planman/Project_list4_list2.htm");
		registry.addViewController("/Project_list3").setViewName("planman/Project_list3_1.htm");
		registry.addViewController("/Project_list2").setViewName("queryandqueto/Project_list2.htm");
		
		
	}
	  
}
