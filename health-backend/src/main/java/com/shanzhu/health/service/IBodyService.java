package com.shanzhu.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.health.entity.Body;

import java.util.List;


public interface IBodyService extends IService<Body> {

    boolean insert(Body body);

    void update(Body body);

    List<Body> getBodyListByUserId(Integer pid);

    Body getBodyById(Integer id);

    void updateBody(Body body);

    void deletBodyById(Integer id);

}
