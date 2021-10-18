FROM amazoncorretto:11-alpine-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8200

RUN addgroup -S app && adduser -S app -G app
USER app

ENTRYPOINT ["java", "-jar", "/app.jar"]
