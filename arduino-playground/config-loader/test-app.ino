
#include <FS.h>
#include <ArduinoJson.h>

JsonObject* ptr_json;

void setup() {
  configLogger(9600);
  configFS();
  ptr_json = loadConfig("/config.json");
}

void loop() {
  JsonObject& json = *ptr_json;
  
  logInfo("ssid: "); logInfo(json["ssid"]);
  logInfo("pass: "); logInfo(json["pass"]);
  logInfo("blynk_token: "); logInfo(json["blynk_token"]);
  logInfo("mqtt_server: "); logInfo(json["mqtt_server"]);
  logInfo("mqtt_portd: "); logInfo(json["mqtt_portd"]);

  delay(1000);
}
