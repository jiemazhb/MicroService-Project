FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} serviceconfig.jar

#CMD

ENTRYPOINT ["java", "-jar", "/serviceconfig.jar"]

EXPOSE 8051