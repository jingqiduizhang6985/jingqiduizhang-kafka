package com.study.kafkademo;

import com.study.kafkademo.config.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class KafkaDemoApplication {
    @Autowired
    private Product product;
    @PostConstruct
    public void init(){
        for(int i=0;i<10;i++){
            product.send("afs"+i);
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(KafkaDemoApplication.class, args);
    }

}
