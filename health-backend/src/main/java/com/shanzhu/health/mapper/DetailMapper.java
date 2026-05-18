package com.shanzhu.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanzhu.health.entity.Detail;

import java.util.List;

public interface DetailMapper extends BaseMapper<Detail> {
    List<Detail> getDetailInfo(String sportName);
}

