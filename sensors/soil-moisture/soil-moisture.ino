#define vccPin 7
#define sensorPin A0

int measure() {
  digitalWrite(vccPin, HIGH);
  delay(100);
  int value = analogRead(sensorPin);
  delay(10);
  digitalWrite(vccPin, LOW);
  return value;
}

void setup() {
  Serial.begin(9600);
  pinMode(vccPin, OUTPUT);
}

void loop() {
  int value = measure();
  Serial.print("Analog Value : ");
  Serial.println(value);  
  delay(2000); 
}
