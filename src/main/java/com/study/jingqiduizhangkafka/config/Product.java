package com.study.jingqiduizhangkafka.config;

import com.alibaba.fastjson.JSON;
import com.study.jingqiduizhangkafka.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 生产者
 */
@Component
public class Product {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(String name){
        User u=new User();
        u.setName(name);
        u.setAge(11);
        kafkaTemplate.send("user", JSON.toJSONString(u));
    }
}
