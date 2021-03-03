# jingqiduizhang-kafka
消息中间件kafkamq

### kafka安装：
#### Linux
********************************************************************************************
#### 1先安装jdk jdk-8u281-linux-x64.tar.gz
##### 1.1 解压jdk
tar zxvf  jdk-8u281-linux-x64.tar.gz
解压后的文件名为 jdk1.8.0_281
#####  1.2 配置环境变量
vi /etc/profile
配置下面内容 并保存退出。
export JAVA_HOME=/usr/java/jdk1.8.0_281
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib:$CLASSPATH
export JAVA_PATH=${JAVA_HOME}/bin:${JRE_HOME}/bin
export PATH=$PATH:${JAVA_PATH}
#####  1.3 source 配置文件
source /etc/profile
#####  1.4 查看jdk版本
java -version
如果出现 java -version  "1.8.0_281" 则说明安装成功。

********************************************************************************************
#### 2.安装zookeeper: zookeeper-3.4.6.tar.gz
#####  2.1 解压文件到 目录 /usr/java/kafka/
tar zxvf zookeeper-3.4.6.tar.gz
2.2 设置环境变量
vi /etc/profile
export ZOOKEEPER_HOME=/usr/java/kafka/zookeeper-3.4.6
source /etc/profile
#####  2.3 修改文件名称
/usr/java/kafka/zookeeper-3.4.6/conf 目录内的
zoo_sample.cfd文件名称改为zoo.cfg
#####  2.4 启动/停止
./zkServer.sh start #启动
netstat -tunlp|grep 2181 #查看zookeeper端口
./zkServer.sh stop #停止
********************************************************************************************
#### 3.安装kafka kafka_2.12-0.11.0.0.tgz
参考资料:
https://www.cnblogs.com/justuntil/p/8033792.html

#####  3.1 解压kafka 到文件夹/usr/java/kafka
tar zxvf kafka_2.12-0.11.0.0.tgz

#####  3.2 配置 server.properties 文件
设置配置host.name=本机ip地址
host.name=192.168.27.128

#####  3.3 启动kafka
bin/kafka-server-start.sh config/server.properties &
#####  3.4 查看监控端口是否开启
netstat -tunlp|egrep "(2181|9092)"
********************************************************************************************
#### 4.开启两个终端测试
##### 4.1 单机测试 开启一个生产者 打开一个shell客户端 进入kafka 文件目录执行
bin/kafka-console-producer.sh --broker-list 192.168.217.128:9092 --topic test
回车后 随便输入测试内容

#####  4.2 单机测试 开启一个消费者 打开一个shell客户端 进入kafka 文件目录执行
bin/kafka-console-consumer.sh --zookeeper 192.168.217.128:2181 --topic test --from-beginning
此时能坚挺到 生产者发送过来的数据。
此时 一个单机版 单个broker 部署完成。
********************************************************************************************
#### 5.运行代码测试
#####  5.1配置kafka-demo2中配置文件 application.properties 中的
spring.kafka.bootstrap-servers=192.168.217.128:9092 指定到kafka主机ip地址

运行代码 kafka-demo2 中的启动类 在打印文件中可以看到生产和消费信息
********************************************************************************************
