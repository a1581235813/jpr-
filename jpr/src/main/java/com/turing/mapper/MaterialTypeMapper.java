package com.turing.mapper;

import com.turing.entity.MaterialType;
import com.turing.entity.MaterialTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MaterialTypeMapper {
    long countByExample(MaterialTypeExample example);

    int deleteByExample(MaterialTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MaterialType record);

    int insertSelective(MaterialType record);

    List<MaterialType> selectByExample(MaterialTypeExample example);

    MaterialType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MaterialType record, @Param("example") MaterialTypeExample example);

    int updateByExample(@Param("record") MaterialType record, @Param("example") MaterialTypeExample example);

    int updateByPrimaryKeySelective(MaterialType record);

    int updateByPrimaryKey(MaterialType record);
}