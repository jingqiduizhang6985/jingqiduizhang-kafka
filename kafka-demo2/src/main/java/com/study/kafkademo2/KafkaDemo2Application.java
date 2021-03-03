package com.study.kafkademo2;

import com.study.kafkademo2.config.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class KafkaDemo2Application {

    @Autowired
    private Product product;
    @PostConstruct
    public void init(){
        for(int i=0;i<10;i++){
            product.send("afs"+i);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaDemo2Application.class, args);
    }

}
