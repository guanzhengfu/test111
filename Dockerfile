FROM openjdk:8u265-jdk-slim

COPY target/test111-0.0.1-SNAPSHOT.jar test111.jar

ENTRYPOINT ["java","-jar","/test111.jar"]

#项目暴露的端口
EXPOSE 8002
