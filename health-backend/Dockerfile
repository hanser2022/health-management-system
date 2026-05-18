FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="hanser2022"
LABEL description="Health Management System - Backend"

WORKDIR /app

COPY target/health-backend-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS="-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Xms128m -Xmx256m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+UseStringDeduplication"
ENV SPRING_PROFILES_ACTIVE=server
ENV SERVER_PORT=8080

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar --server.port=${SERVER_PORT} --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
