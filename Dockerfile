FROM raspbian/stretch:latest

WORKDIR /app

COPY ./client-api.jar /app

RUN sudo apt update
RUN sudo apt install default-jdk -y

CMD ["java", "-jar", "client-api.jar"]
