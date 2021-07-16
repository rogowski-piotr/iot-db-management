#include <ESP8266WiFi.h>
#include <ArduinoJson.h>

#define SSID "ssid_name"
#define PASSWORD "secret"
#define PORT 50007

#define VCC_PIN D7
#define SENSOR_PIN A0
#define MAXIMUM_ACCEPTABLE_VALUE 1020
#define MINIMUM_ACCEPTABLE_VALUE 100

WiFiServer server(PORT);

int value;
bool isWorking = true;

void checkMeasurement() {
  if (value >= MAXIMUM_ACCEPTABLE_VALUE || value <= MINIMUM_ACCEPTABLE_VALUE) {
    isWorking = false;
    value = 0;
  } else {
    isWorking = true;
  }
}

void measure() {
  digitalWrite(VCC_PIN, HIGH);
  delay(50);
  value = analogRead(A0);
  digitalWrite(VCC_PIN, LOW);
  checkMeasurement();
}

void serializeAndSend(WiFiClient client) {
  const size_t capacity = JSON_OBJECT_SIZE(4);
  DynamicJsonDocument doc(capacity);
  doc["sensor"] = "soil-moisture";
  doc["active"] = isWorking;
  doc["soil-moisture"] = value;
  serializeJson(doc, client);
  serializeJson(doc, Serial);
}

void setup() {
  Serial.begin(9600);
  pinMode(VCC_PIN, OUTPUT);
  pinMode(SENSOR_PIN, INPUT);
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
  measure();
  serializeAndSend(client);
  Serial.println("\nDisconnected client");
}
