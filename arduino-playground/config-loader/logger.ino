
void configLogger(const int baudWidth) {
  Serial.begin(baudWidth);
}

void logInfo(const char* message) {
  Serial.println(message);
}

