package com.shanzhu.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanzhu.health.entity.Body;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BodyMapper extends BaseMapper<Body> {
    List<Body> getBodyListByUserId(@Param("pid") Integer pid);

}

