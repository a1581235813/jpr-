package com.turing.mapper;

import com.turing.entity.Orders;
import com.turing.entity.OrdersExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OrdersMapper {
    long countByExample(OrdersExample example);

    int deleteByExample(OrdersExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Orders record);

    int insertSelective(Orders record);

    List<Orders> selectByExample(OrdersExample example);

    Orders selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByExample(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
    
    List<Map> select(@Param("name1") String name1,@Param("name2")String name2,@Param("state") String state,@Param("st") int st);
    
    List<Map> select2(@Param("name1") String name1,@Param("name2")String name2,@Param("px") String px);
    
    List<Map> select3();
}