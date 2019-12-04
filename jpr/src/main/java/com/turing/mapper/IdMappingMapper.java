package com.turing.mapper;

import com.turing.entity.IdMapping;
import com.turing.entity.IdMappingExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IdMappingMapper {
    long countByExample(IdMappingExample example);

    int deleteByExample(IdMappingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IdMapping record);

    int insertSelective(IdMapping record);

    List<IdMapping> selectByExample(IdMappingExample example);

    IdMapping selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IdMapping record, @Param("example") IdMappingExample example);

    int updateByExample(@Param("record") IdMapping record, @Param("example") IdMappingExample example);

    int updateByPrimaryKeySelective(IdMapping record);

    int updateByPrimaryKey(IdMapping record);
    
    IdMapping findByStockId(@Param("id") long id);
    List<Map> idorder(@Param("id") long id);
}