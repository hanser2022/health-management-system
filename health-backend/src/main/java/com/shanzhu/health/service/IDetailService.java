package com.shanzhu.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.health.entity.Detail;

import java.util.List;


public interface IDetailService extends IService<Detail> {
    List<Detail> getDetailInfo(String sportName);

    void updateDetail(Detail detail);

    Detail getDetailById(Integer id);

    void deletDetailById(Integer id);

    boolean addDetail(Detail detail);
}



