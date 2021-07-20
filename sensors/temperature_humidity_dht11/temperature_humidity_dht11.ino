#include "DHT.h"
#include <ESP8266WiFi.h>
#include <ArduinoJson.h>

#define SSID "ssid_name"
#define PASSWORD "secret"
#define PORT 50007

#define DHTPIN D8
#define DHTTYPE DHT11

WiFiServer server(PORT);
DHT dht(DHTPIN, DHTTYPE);

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
  serializeJson(doc, Serial);
}

void setup() {
  Serial.begin(9600);
  dht.begin();
  Serial.println(String("Connecting to ssid: ") + String(SSID));
  WiFi.begin(SSID, PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  server.begin();
  Serial.println("\nConnected!\nServer at socket: " + WiFi.localIP().toString() + ":" + PORT);
}

void loop() {
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
  Serial.print("\nConnected client: ");
  Serial.println(client.remoteIP());
  client.flush();
  measureDHT();
  serializeAndSend(client);
  Serial.println("\nDisconnected client");
}
