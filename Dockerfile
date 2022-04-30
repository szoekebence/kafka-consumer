FROM openjdk:16-alpine3.13
WORKDIR /app
ADD /target/kafka-consumer-fatjar.jar /app/kafka-consumer.jar
CMD ["java", "-jar", "/app/kafka-consumer.jar"]