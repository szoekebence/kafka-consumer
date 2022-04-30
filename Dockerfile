FROM openjdk:16-alpine3.13
WORKDIR /app
COPY /private_data /app/private_data
ADD /target/kafka-consumer-fatjar.jar /app/kafka-consumer.jar
CMD ["java", "-jar", "/app/kafka-consumer.jar"]