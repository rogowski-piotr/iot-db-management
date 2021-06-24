## Test environment

#### Usage
Run maven install to automatic deploy application to remote test environment.
```java
mvn install
```

##### Add ssh keys 
```shell script
> ssh-keygen
> type C:\Users\<user>\.ssh\id_rsa.pub | ssh <user>@<host> "cat >> .ssh/authorized_keys"
```

##### Build Docker image and containers
```shell script
> docker build --rm -t test-env .
> docker run -d --name client-api -p 8080:8080 -v /home/pi/workspace:/app test-env
> docker run -d --name sensor-managment -v /home/pi/workspace:/app test-env
```
