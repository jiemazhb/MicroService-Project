FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} apigateway.jar

#CMD

ENTRYPOINT ["java", "-jar", "/apigateway.jar"]

EXPOSE 8052