FROM openjdk:8

EXPOSE 8090

ARG SERVICE_ACCOUNT

COPY ${SERVICE_ACCOUNT} .

ENV GOOGLE_APPLICATION_CREDENTIALS=${SERVICE_ACCOUNT}

ARG JAR_FILE=RevOverflow-0.0.1-SNAPSHOT.jar

COPY /target/${JAR_FILE} revoverflow-backend.jar

CMD java -jar revoverflow-backend.jar

