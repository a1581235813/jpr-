package com.turing.util;

import java.util.ArrayList;
import java.util.List;

import com.turing.entity.SysMenus;

public class TreeUtil {
	public static List<SysMenus> findParent(List<SysMenus> allList){
		List<SysMenus> parents = new ArrayList<>();
		for (SysMenus sysMenus : allList) {
			if(sysMenus.getParentId()==1){
				sysMenus.setText(sysMenus.getName());
				//寻找一级菜单下的子节点
				sysMenus.setChildren(findSons(sysMenus.getId(), allList));
				parents.add(sysMenus);
			}
		}
		return parents;
	}
	
	//在所有菜单下 寻找指定父节点的子节点
	public static List<SysMenus> findSons(long ParentId,List<SysMenus> allList){
		List<SysMenus> sons = new ArrayList<>();
		for(SysMenus menu:allList) {
			if(menu.getParentId()== 1) {
				continue;
			}
			if(menu.getParentId() == ParentId) {
				//继续再找该菜单下子节点
				//递归算法
				menu.setText(menu.getName());
				menu.setChildren(findSons(menu.getId(), allList));
				//添加到集合
				sons.add(menu);
			}
		}
		return sons;
	}
	//测试
}
