#网易镜像中心的Java:指明在那个镜像的基础上来构建镜像
FROM hub.c.163.com/library/java:latest
#自己项目Jar包的路径,eurrka.jar可以自定义
ADD target/*.jar eurekaServer-0.0.1-SNAPSHOT.jar
#端口
EXPOSE 8761
#运行Jar:本例中代表的是执行java运行jar包的命令
ENTRYPOINT ["java", "-jar", "/eurekaServer-0.0.1-SNAPSHOT.jar"]