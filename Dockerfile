FROM openjdk:8

EXPOSE 8090

ARG JAR_FILE=RevOverflow-0.0.1-SNAPSHOT.jar

COPY /target/${JAR_FILE} revoverflow-backend.jar

CMD java -jar revoverflow-backend.jar
#ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/myservice.jar"]

