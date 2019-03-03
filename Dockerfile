FROM gradle:5.2.1-jdk11-slim AS gradle-stage
COPY *gradl* /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN gradle build
FROM openjdk:11-jre-slim
COPY --from=gradle-stage /tmp/build/libs/*.jar /app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", " java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
EXPOSE 8092
