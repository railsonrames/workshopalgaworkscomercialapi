FROM ringcentral/jdk
ADD target/comercial-api.jar comercial-api.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "comercial-api.jar"]