package com.turing.mapper;

import com.turing.entity.Stock;
import com.turing.entity.StockExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface StockMapper {
    long countByExample(StockExample example);

    int deleteByExample(StockExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Stock record);

    int insertSelective(Stock record);

    List<Stock> selectByExample(StockExample example);

    Stock selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByExample(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);
    
    List<Map<String, Object>> findall(@Param("name") String name,@Param("stas") String stas);
    
    List<Map<String, Object>> idStock( @Param("id") long id);
    
    List<Map<String, Object>> findallStock();
    
    List<Map>  findallStock2();
    List<Map>  findallStock3();
    
    List<Map>  findallStock4( @Param("name")String name,@Param("type") String type);

}