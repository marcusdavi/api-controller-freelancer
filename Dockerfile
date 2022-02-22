FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ./target/api-controle-freelancer-0.0.1-SNAPSHOT.jar api-controle-freelancer.jar
ENTRYPOINT ["java","-jar","/api-controle-freelancer.jar"]