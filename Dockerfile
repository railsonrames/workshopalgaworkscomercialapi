FROM openjdk:14-jdk-alpine3.10
ADD target/comercial-api.jar comercial-api.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "comercial-api.jar"]