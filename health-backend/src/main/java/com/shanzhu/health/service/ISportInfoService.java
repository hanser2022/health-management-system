package com.shanzhu.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.health.entity.SportInfo;

import java.util.List;
import java.util.Map;

public interface ISportInfoService extends IService<SportInfo> {
    List<SportInfo> getAllSportInfos();

    boolean addSport(SportInfo sport);

    void updateSport(SportInfo sport);

    SportInfo getSportById(Integer id);

    void deletUserById(Integer id);

    List<Map<String, Object>> recommendSports(Integer userId);
}
