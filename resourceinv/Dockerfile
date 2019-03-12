FROM openjdk:8-jre-alpine AS ResourceInv
VOLUME /tmp
EXPOSE 8090
COPY ./target/resourceinv-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
