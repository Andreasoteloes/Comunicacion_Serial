const int ledPins[] = {2, 3, 4, 5};  // Definir los pines de los 4 LEDs

void setup() {
  Serial.begin(9600);
  for (int i = 0; i < 4; i++) {
    pinMode(ledPins[i], OUTPUT);
  }
}

void loop() {
  if (Serial.available() > 0) {
    char command = Serial.read();
    
    if (command >= '1' && command <= '4') {
      int ledIndex = command - '1';
      digitalWrite(ledPins[ledIndex], !digitalRead(ledPins[ledIndex])); // Invertir el estado del LED
    }
  }
}
