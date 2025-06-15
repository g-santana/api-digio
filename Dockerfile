FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY build/libs/*.jar app.jar
COPY sync.sh .

RUN chmod +x sync.sh

EXPOSE 8080

ENTRYPOINT sh -c "./sync.sh postgres:5432 -- java -jar app.jar"
