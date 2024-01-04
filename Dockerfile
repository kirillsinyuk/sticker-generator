FROM gradle:8.1.1-jdk17
COPY ./ ./
RUN gradle build

FROM openjdk:17

ARG JAR=/build/libs/*.jar
COPY $JAR sticker-generator.jar
EXPOSE 8080

CMD java -jar /sticker-generator.jar