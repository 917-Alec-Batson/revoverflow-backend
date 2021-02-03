FROM openjdk:8

EXPOSE 8090

ENV GOOGLE_APPLICATION_CREDENTIALS=/creds.json

ARG JAR_FILE=RevOverflow-0.0.1-SNAPSHOT.jar

COPY /target/${JAR_FILE} revoverflow-backend.jar

CMD java -jar revoverflow-backend.jar

