FROM openjdk:18
WORKDIR /app
ADD /target/kafka-consumer-fatjar.jar /app/kafka-consumer.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/kafka-consumer.jar"]