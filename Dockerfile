FROM gradle:9.1.0-jdk25 AS build
COPY ./ ./
RUN gradle build

FROM eclipse-temurin:25-jdk

ARG JAR=/build/libs/*.jar
COPY $JAR sticker-generator.jar
EXPOSE 8080

CMD java -jar /sticker-generator.jar
