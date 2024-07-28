FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} serviceregistry.jar

#CMD

ENTRYPOINT ["java", "-jar", "/serviceregistry.jar"]

EXPOSE 8050