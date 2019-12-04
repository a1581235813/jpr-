package com.turing.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.biz.SysusersBiz;
import com.turing.entity.SysUsers;
import com.turing.entity.SysUsersExample;
import com.turing.entity.SysUsersExample.Criteria;
import com.turing.mapper.SysUsersMapper;
@Service
public class SysUsersBizImpl implements SysusersBiz{
    @Autowired
	SysUsersMapper mapper;
	@Override
	public SysUsers login(SysUsers user) {
		// TODO Auto-generated method stub
		SysUsersExample example=new SysUsersExample();
		 Criteria cr=example.createCriteria();
		 cr.andLoginIdEqualTo(user.getLoginId());
		 cr.andPasswordEqualTo(user.getPassword());
	     List<SysUsers>  list=mapper.selectByExample(example);
	    
		return list.size()!=0?list.get(0):null;
	      
	}

	

}
