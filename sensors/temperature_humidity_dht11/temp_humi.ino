#include "DHT.h"
#include <ESP8266WiFi.h>
#include <ArduinoJson.h>

#define DHTPIN D8
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);

#define PORT 50007
WiFiServer server(PORT);
const char* ssid = "ssid_name";
const char* password = "secret";

bool isWorking = true;
float temperature, humidity;

void measureDHT() {
  temperature = dht.readTemperature();
  humidity = dht.readHumidity();
  isWorking = isnan(temperature) || isnan(humidity) ? false : true;
}

void serializeAndSend(WiFiClient client) {
  const size_t capacity = JSON_OBJECT_SIZE(4);
  DynamicJsonDocument doc(capacity);
  doc["sensor"] = "DHT11";
  doc["active"] = isWorking;
  doc["temperature"] = temperature;
  doc["humidity"] = humidity;
  serializeJson(doc, client);
}

void setup() {
  Serial.begin(9600);
  dht.begin();
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500); 
  }
  server.begin();
  Serial.println("Web socket:");
  Serial.print(WiFi.localIP());
  Serial.print(":");
  Serial.println(PORT);
}

void loop() {
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
  Serial.println("new client");
  client.flush();
  measureDHT();
  serializeAndSend(client);
  Serial.println("Client disconnected\n");
}
