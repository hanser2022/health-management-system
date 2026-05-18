package com.shanzhu.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.health.entity.Detail;
import com.shanzhu.health.mapper.DetailMapper;
import com.shanzhu.health.service.IDetailService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DetailServiceImpl extends ServiceImpl<DetailMapper, Detail> implements IDetailService {


    @Resource
    private DetailMapper detailMapper;


    @Override
    public List<Detail> getDetailInfo(String sportName) {

        return detailMapper.getDetailInfo(sportName);
    }


    @Transactional
    @Override
    public boolean addDetail(Detail detail) {
        QueryWrapper<Detail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sport_type", detail.getSportType());
        List<Detail> list = this.baseMapper.selectList(queryWrapper);
        if (list.isEmpty()) {
            this.baseMapper.insert(detail);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void updateDetail(Detail detail) {
        this.baseMapper.updateById(detail);
    }

    @Override
    public Detail getDetailById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public void deletDetailById(Integer id) {
        this.baseMapper.deleteById(id);
    }
}
