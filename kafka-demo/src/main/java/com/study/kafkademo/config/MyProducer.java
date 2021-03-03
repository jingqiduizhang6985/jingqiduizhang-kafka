package com.study.kafkademo.config;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * Producer生产者 
 *
 * 发送消息的方式，只管发送，不管结果：只调用接口发送消息到 Kafka 服务器，但不管成功写入与否。由于 Kafka 是高可用的，因此大部分情况下消息都会写入，但在异常情况下会丢消息
 * 同步发送：调用 send() 方法返回一个 Future 对象，我们可以使用它的 get() 方法来判断消息发送成功与否
 * 异步发送：调用 send() 时提供一个回调方法，当接收到 broker 结果后回调此方法
 */
public class MyProducer {
    private static KafkaProducer<String, String> producer;
    //初始化
    static {
        Properties properties = new Properties();
        //kafka启动，生产者建立连接broker的地址
        properties.put("bootstrap.servers", "192.168.217.128:9092");
        //kafka序列化方式
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //自定义分区分配器
        properties.put("partitioner.class", "com.study.kafkademo.config.CustomPartitioner");
        producer = new KafkaProducer<>(properties);
    }

    /**
     * 创建topic：.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181
     * --replication-factor 1 --partitions 1 --topic kafka-study
     * 创建消费者：.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092
     * --topic imooc-kafka-study --from-beginning
     */
    //发送消息，发送完后不做处理
    private static void sendMessageForgetResult() {
        ProducerRecord<String, String> record = new ProducerRecord<>("kafka-study", "name", "ForgetResult");
        producer.send(record);
        producer.close();
    }
    //发送同步消息，获取发送的消息
    private static void sendMessageSync() throws Exception {
        ProducerRecord<String, String> record = new ProducerRecord<>("kafka-study", "name", "sync");
        RecordMetadata result = producer.send(record).get();
        System.out.println(result.topic());//imooc-kafka-study
        System.out.println(result.partition());//分区为0
        System.out.println(result.offset());//已发送一条消息，此时偏移量+1
        producer.close();
    }
    /**
     * 创建topic：.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181
     * --replication-factor 1 --partitions 3 --topic kafka-study-x
     * 创建消费者：.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092
     * --topic kafka-study-x --from-beginning
     */
    private static void sendMessageCallback() {
        ProducerRecord<String, String> record = new ProducerRecord<>("kafka-study-x", "name", "callback");
        producer.send(record, new MyProducerCallback());
        //发送多条消息
        record = new ProducerRecord<>("kafka-study-x", "name-x", "callback");
        producer.send(record, new MyProducerCallback());
        producer.close();
    }
    //发送异步消息
    //场景：每条消息发送有延迟，多条消息发送，无需同步等待，可以执行其他操作，程序会自动异步调用
    private static class MyProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                e.printStackTrace();
                return;
            }
            System.out.println("*** MyProducerCallback ***");
            System.out.println(recordMetadata.topic());
            System.out.println(recordMetadata.partition());
            System.out.println(recordMetadata.offset());
        }
    }
    public static void main(String[] args) throws Exception {
        sendMessageForgetResult();
        //sendMessageSync();
//        sendMessageCallback();

    }
}


