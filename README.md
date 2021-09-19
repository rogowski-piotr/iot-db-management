# <p align="center"> 💡 IoT management 💡 </p>
> This is implementation of platform to management IoT sensors.

## Contents
* [General Info](#general-information)
  * [Modules](#general-information)
  * [Security](#general-information)
  * [Environment](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Configuration](#Configuration)


## General Information
### 📁 Modules 📁
* [client-api](client-api) - Provide Web API for client applications. Also allow performing CRUD operation
* [model](model) - Shared module containing all application models and database configuration
* [sensor-management](sensor-management) - Provides executing measurements on sensors
* [sensors](sensors) - Contains documentation and code for each of the available sensors to be configured on the ESP8266 or ESP32 platform
* [test-env](test-env) - Contains documentation and automation scripts to configure continuous integration on Raspberry pi environment

### 🔐 Security 🔐
* Access control to API by providing login and password by [basic authentication](https://en.wikipedia.org/wiki/Basic_access_authentication).
* Security of stored passwords is ensured using [MD55](https://en.wikipedia.org/wiki/MD5) hashing algorithm.

### ▶️ Environment ▶️
Currently, platform runs in the docker layer on the Raspberry pi platform. [configuration](test-env).  
Each sensor based on platform esp8266 or esp32 with support wireless WiFi connection. [configuration](sensors).  
Main platform communicates with sensors using the TCP network protocol.


## Technologies used
* Raspberry Pi
* Docker
* Arduino
* Postgres
* Java 11
  * Spring Boot
  * Spring Security
  * Lombok
  * JPA (Hibernate)


## Features
wykonywanie pomiarów(tcp, przez arduino i treggered przez platforme)
automatyczne wykrywanie dostępności czujnika
konfigurowanie czujników (zmiana położenia, adresu, czestotliwosci pomiaru)
konfigurowanie automatycznego wykrywania czujnika gdy jest nieaktywny
Uzytkownicy oraz autowyzowany dostep

## Configuration

