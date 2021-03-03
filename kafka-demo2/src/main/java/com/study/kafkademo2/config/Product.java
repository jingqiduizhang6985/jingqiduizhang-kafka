package com.study.kafkademo2.config;

import com.alibaba.fastjson.JSON;
import com.study.kafkademo2.entity.User;
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
        System.out.println("发送/生产数据 " + u.toString());
        kafkaTemplate.send("user", JSON.toJSONString(u));
    }
}
