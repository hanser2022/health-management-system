package com.shanzhu.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.health.entity.Body;
import com.shanzhu.health.entity.SportInfo;
import com.shanzhu.health.mapper.BodyMapper;
import com.shanzhu.health.mapper.SportInfoMapper;
import com.shanzhu.health.service.ISportInfoService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SportInfoServiceImpl extends ServiceImpl<SportInfoMapper, SportInfo> implements ISportInfoService {

    @Resource
    private SportInfoMapper sportInfoMapper;

    @Resource
    private BodyMapper bodyMapper;

    @Override
    public List<SportInfo> getAllSportInfos() {
        return sportInfoMapper.selectList(null);
    }

    @Transactional
    @Override
    public boolean addSport(SportInfo sport) {
        LambdaQueryWrapper<SportInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SportInfo::getSportType, sport.getSportType());
        List<SportInfo> list = this.baseMapper.selectList(queryWrapper);
        if (list.isEmpty()) {
            this.baseMapper.insert(sport);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateSport(SportInfo sport) {
        this.baseMapper.updateById(sport);
    }

    @Override
    public SportInfo getSportById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public void deletUserById(Integer id) {
        this.baseMapper.deleteById(id);
    }

    @Override
    public List<Map<String, Object>> recommendSports(Integer userId) {
        List<Body> bodyList = bodyMapper.getBodyListByUserId(userId);
        if (bodyList == null || bodyList.isEmpty()) {
            return Collections.emptyList();
        }

        Body body = bodyList.get(0);
        List<SportInfo> allSports = sportInfoMapper.selectList(null);

        if (allSports.isEmpty()) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> scored = allSports.stream().map(sport -> {
            int score = 100;
            String reasons = "";

            double heartRate = body.getHeartRate();
            if (heartRate > 0) {
                String heartRateStr = sport.getSuitableHeartRate();
                if (heartRateStr != null && !heartRateStr.isEmpty()) {
                    try {
                        String[] parts = heartRateStr.split("-");
                        if (parts.length == 2) {
                            int minHR = Integer.parseInt(parts[0].trim());
                            int maxHR = Integer.parseInt(parts[1].trim());
                            if (heartRate >= minHR && heartRate <= maxHR) {
                                score += 20;
                                reasons += "心率匹配; ";
                            } else if (heartRate > maxHR) {
                                score -= 30;
                                reasons += "心率偏高，建议低强度运动; ";
                            }
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            int age = body.getAge() != null ? body.getAge() : 30;
            if (age > 50) {
                if (sport.getSuitableFrequency() != null &&
                        (sport.getSuitableFrequency().contains("低") || sport.getSuitableFrequency().contains("每周1"))) {
                    score += 15;
                    reasons += "适合中老年人; ";
                }
                if (sport.getSportType() != null &&
                        (sport.getSportType().contains("冲刺") || sport.getSportType().contains("极限"))) {
                    score -= 40;
                    reasons += "高强度运动不适合中老年人; ";
                }
            } else if (age < 25) {
                score += 10;
                reasons += "年轻人适合多种运动; ";
            }

            if (body.isSmoking()) {
                if (sport.getSuitableHeartRate() != null) {
                    try {
                        String[] parts = sport.getSuitableHeartRate().split("-");
                        if (parts.length == 2) {
                            int maxHR = Integer.parseInt(parts[1].trim());
                            if (maxHR > 140) {
                                score -= 15;
                                reasons += "吸烟者不宜高强度运动; ";
                            }
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            double sleepDuration = body.getSleepDuration();
            if (sleepDuration > 0 && sleepDuration < 6) {
                if (sport.getSuitableTime() != null) {
                    try {
                        int time = Integer.parseInt(sport.getSuitableTime().replaceAll("[^0-9]", ""));
                        if (time > 60) {
                            score -= 10;
                            reasons += "睡眠不足不宜长时间运动; ";
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            double heightM = body.getHeight() / 100.0;
            double bmi = heightM > 0 ? body.getWeight() / (heightM * heightM) : 0;
            if (bmi > 28) {
                if (sport.getSportType() != null &&
                        (sport.getSportType().contains("游泳") || sport.getSportType().contains("快走") || sport.getSportType().contains("骑行"))) {
                    score += 15;
                    reasons += "适合肥胖人群; ";
                }
                if (sport.getSportType() != null && sport.getSportType().contains("跳绳")) {
                    score -= 20;
                    reasons += "肥胖人群不宜跳绳; ";
                }
            } else if (bmi > 24 && bmi <= 28) {
                score += 5;
                reasons += "适合减脂运动; ";
            }

            Map<String, Object> item = new HashMap<>();
            item.put("sport", sport);
            item.put("score", score);
            item.put("reason", reasons.trim());
            return item;
        }).sorted((a, b) -> (int) b.get("score") - (int) a.get("score"))
          .collect(Collectors.toList());

        return scored.subList(0, Math.min(5, scored.size()));
    }
}
