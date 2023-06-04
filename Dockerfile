FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} book-service.jar
ENTRYPOINT ["java", "-jar", "/book-service.jar"]
EXPOSE 8080