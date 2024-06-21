FROM maven:3.8.5-openjdk-17-slim as builder
WORKDIR /src
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn package -DskipTests

FROM alpine:3.15.3
RUN apk --no-cache add openjdk17-jre
COPY --from=builder /src/target/*SNAPSHOT-exec.jar app.jar
EXPOSE 9898
ENTRYPOINT ["java","-jar","/app.jar"]