package com.shanzhu.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.health.entity.Body;
import com.shanzhu.health.entity.BodyNotes;
import com.shanzhu.health.mapper.BodyMapper;
import com.shanzhu.health.mapper.BodyNotesMapper;
import com.shanzhu.health.service.IBodyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyServiceImpl extends ServiceImpl<BodyMapper, Body> implements IBodyService {


    @Resource
    private BodyMapper bodyMapper;
    @Resource
    private BodyNotesMapper bodyNotesMapper;


    @Override
    public boolean insert(Body body) {
        LambdaQueryWrapper<Body> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Body::getId, body.getId());
        int count = this.baseMapper.selectCount(wrapper).intValue();
        if (count > 0) {
            Body oldBody = this.baseMapper.selectOne(wrapper);
            if (oldBody != null) {
                BodyNotes bodyNotes = new BodyNotes();
                bodyNotes.setId(oldBody.getId());
                bodyNotes.setName(oldBody.getName());
                bodyNotes.setAge(oldBody.getAge());
                bodyNotes.setGender(oldBody.getGender());
                bodyNotes.setHeight(oldBody.getHeight());
                bodyNotes.setWeight(oldBody.getWeight());
                bodyNotes.setBloodSugar(oldBody.getBloodSugar());
                bodyNotes.setBloodPressure(oldBody.getBloodPressure());
                bodyNotes.setBloodLipid(oldBody.getBloodLipid());
                bodyNotes.setHeartRate(oldBody.getHeartRate());
                bodyNotes.setVision(oldBody.getVision());
                bodyNotes.setSleepDuration(oldBody.getSleepDuration());
                bodyNotes.setSleepQuality(oldBody.getSleepQuality());
                bodyNotes.setSmoking(oldBody.isSmoking());
                bodyNotes.setDrinking(oldBody.isDrinking());
                bodyNotes.setExercise(oldBody.isExercise());
                bodyNotes.setFoodTypes(oldBody.getFoodTypes());
                bodyNotes.setWaterConsumption(oldBody.getWaterConsumption());
                bodyNotesMapper.insert(bodyNotes);
            }
            this.baseMapper.update(body, wrapper);
            return false;
        } else {
            this.baseMapper.insert(body);
            return true;
        }
    }


    @Override
    public void update(Body body) {
        this.baseMapper.updateById(body);
    }


    @Override
    public List<Body> getBodyListByUserId(Integer pid) {
        return bodyMapper.getBodyListByUserId(pid);
    }


    @Override
    public Body getBodyById(Integer id) {
        Body body = this.baseMapper.selectById(id);
        return body;
    }

    @Override
    public void updateBody(Body body) {
        this.baseMapper.updateById(body);
    }

    @Override
    public void deletBodyById(Integer id) {
        // 直接删除指定主键的记录
        this.baseMapper.deleteById(id);
        //删除BodyNotes表中与
    }


}

