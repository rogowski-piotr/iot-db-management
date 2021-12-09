FROM maven:3.8.4-jdk-11

EXPOSE 8080

WORKDIR /app

COPY . /app

RUN mvn package -f /app