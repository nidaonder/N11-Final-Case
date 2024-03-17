# 1. Aşama: Maven ile uygulamayı paketle
FROM maven:3.8.4-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
WORKDIR /home/app
RUN mvn clean package

# 2. Aşama: Java 17 ile uygulamayı çalıştır
FROM openjdk:17
COPY --from=build /home/app/target/*.jar /usr/local/lib/restaurant.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/restaurant.jar"]
