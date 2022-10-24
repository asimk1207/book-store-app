FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/book-store-app-1.0.0.0.jar
ADD ${JAR_FILE} book-store-app.jar
ENTRYPOINT ["java","-jar","/book-store-app.jar"]