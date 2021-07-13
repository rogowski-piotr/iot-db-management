#include <ESP8266WiFi.h>
#include <ArduinoJson.h>

#define VCC_PIN D7
#define SENSOR_PIN A0
#define SSID "ssid_name"
#define PASSWORD "secret"
#define PORT 50007

WiFiServer server(PORT);

int value;
bool isWorking = true;

void measure() {
  digitalWrite(VCC_PIN, HIGH);
  delay(50);
  value = analogRead(A0);
  digitalWrite(VCC_PIN, LOW);
}

void serializeAndSend(WiFiClient client) {
  const size_t capacity = JSON_OBJECT_SIZE(4);
  DynamicJsonDocument doc(capacity);
  doc["sensor"] = "DHT11";
  doc["active"] = isWorking;
  doc["soil-moisture"] = value;
  serializeJson(doc, client);
}

void setup() {
  Serial.begin(9600);
  pinMode(VCC_PIN, OUTPUT);
  pinMode(SENSOR_PIN, INPUT);
  WiFi.begin(SSID, PASSWORD);
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
  measure();
  serializeAndSend(client);
  Serial.println("Client disconnected\n");
}
