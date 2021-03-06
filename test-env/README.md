# Test environment

### Usage
Run maven install to automatic deploy application to remote test environment.
```java
mvn install
```

### Configuration:

#### 1. Add SSH keys between the development-env and the test-env
```shell script
> ssh-keygen
> type C:\Users\<user>\.ssh\id_rsa.pub | ssh <user>@<host> "cat >> .ssh/authorized_keys"
```

#### 2. Move to this localization and fetch all files
if you want work in different localization change all path in all pom.xml files
```shell script
$ mkdir /home/pi/workspace
$ cd /home/pi/workspace
$ git init
$ git remote add origin https://github.com/rogowski-piotr/iot-db-management.git
$ git fetch origin configure-test-env
$ git checkout origin/configure-test-env -- test-env/
$ mv test-env/* .
$ rm -rf test-env/
$ chmod +x reload.sh
```

#### 3. Build Docker environment
```shell script
$ docker build --rm -t test-env .
$ docker-compose up
```

#### 5. Fix date in containers
```shell script
$ docker exec -it sensor-managment date -s 'yyyy-MM-dd HH:mm:ss'
$ docker exec -it client-api date -s 'yyyy-MM-dd HH:mm:ss'
```

#### 6. Add schema in postgres
```shell script
$ PGPASSWORD=postgres psql -h localhost -p 5432 -U root postgres
=# CREATE SCHEMA sensors;
```
