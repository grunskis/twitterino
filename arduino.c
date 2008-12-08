int inPin = 2;
int ledPin = 13;

int ledMode = LOW;

void setup() {
  Serial.begin(9600);
  
  pinMode(ledPin, OUTPUT);
  digitalWrite(ledPin, ledMode);
}

void loop() {
  if (Serial.available()) {
    char value = Serial.read();
  
    if (value == '1') {
      ledMode = HIGH;
    } else if (value == '0') {
      ledMode = LOW;
    }
  } else {
    if (ledMode == HIGH && digitalRead(inPin) == LOW) {
      ledMode = LOW;
    }
  }
  
  digitalWrite(ledPin, ledMode);
}
