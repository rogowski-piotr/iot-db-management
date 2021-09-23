# <p align="center"> 🤖 Sensors 🤖 </p>
<p align="justify">
   Each folder contains information and source code that allows to obtain data from associated sensor.
   <br/>
   Codes has been validated on <a href="https://www.amazon.com/ARCELI-ESP8266-Development-Compatible-Arduino/dp/B07J2QKNHB">esp8266</a> and <a href="https://www.amazon.com/HiLetgo-ESP-32-Development-Bluetooth-Arduino/dp/B07WFZCBH8/ref=pd_lpo_1?pd_rd_i=B07WFZCBH8&psc=1">esp32</a> platforms.
   <br/>
   It's recommended to deploy programs using analog read on esp8266.
</p>

### Supported sensors:
#### - [soil moisture](https://github.com/rogowski-piotr/iot-db-management/tree/master/sensors/soil-moisture)
#### - [temperature and humidity](https://github.com/rogowski-piotr/iot-db-management/tree/master/sensors/temperature_humidity_dht11)

<br/>

## 🔧 Configuration - Arduino IDE 🔧
#### 1. Add board's manager URL as following:  
   <code> Files > Preferences > Additional Board Manager URLs  </code>

   and insert following phrase:
   ```
   https://dl.espressif.com/dl/package_esp32_index.json, http://arduino.esp8266.com/stable/package_esp8266com_index.json
   ```
  
#### 2. Add board manager as following:
   <code> Tools > Board > Boards Manager </code>  
   Depending on used platform search and install:  
   - ESP32 by Espressif Systems
   - esp8266 by ESP8266 Community (2.7.4 version)

#### 3. Select board as following:
   <code> Tools > Board </code>
   - WeMos d1 r1
   - WEMOS D1 MINI ESP32

#### 4. Select port as following:  
   <code> Tools > Port </code>

#### 5. Depending on used platform in order to connect to the WiFi use appropriate library:
   ```cpp
   #include ESP8266WiFi    // ESP8266
   #include WiFi.h         // ESP32
   ```
#### 6. Click installation button