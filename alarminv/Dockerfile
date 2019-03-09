FROM openjdk:8-jre-alpine AS AlarmInv
VOLUME /tmp
EXPOSE 8090
COPY ./target/alarminv-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
