# 使用openjdk8的镜像
FROM openjdk:8-jre

# 设置工作目录
WORKDIR /build

# 将jar包复制到容器中
ADD ./SparkSend-web/target/SparkSend-web-1.0-SNAPSHOT.jar ./sparksend.jar

# 运行jar包
ENTRYPOINT ["sh","-c","java -jar /sparksend.jar "]