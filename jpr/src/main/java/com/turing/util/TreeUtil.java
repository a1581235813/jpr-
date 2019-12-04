package com.turing.util;

import java.util.ArrayList;

import java.util.List;

import com.turing.entity.SysMenus;

public class TreeUtil {
             public static List<SysMenus> findParent(List<SysMenus> list){
            	 List<SysMenus> list2=new ArrayList<SysMenus>();
            	 for (SysMenus menus : list) {
					if(menus.getParentId()==1) {
						
							menus.setChildren(findSons(menus.getId(), list));
							menus.setText(menus.getName());
							
							menus.setState("closed");
							list2.add(menus);
						
						
						
							
						
					}
				}
				return list2;
            	 
             }
             public static List<SysMenus> findSons (Long id,List<SysMenus> list){
            	 List<SysMenus> list2=new ArrayList<SysMenus>();
            	 for (SysMenus menus : list) {
					if(menus.getParentId()==0) {
						continue;
					}
					if(menus.getParentId()==id) {
						menus.setChildren(findSons(menus.getId(), list));
						
						menus.setText(menus.getName());
						
					
						list2.add(menus);
					}
				}
				return list2;
            	 
             }
}
