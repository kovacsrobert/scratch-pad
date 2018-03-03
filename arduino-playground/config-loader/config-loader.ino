
#include <FS.h>
#include <ArduinoJson.h>

JsonObject* loadConfig(const char* filePath) {
  File* ptr_configFile = loadFile(filePath);
  if (ptr_configFile == NULL) {
    logInfo("Failed loading config json");
    return NULL;
  }
  File configFile = *ptr_configFile;
  size_t size = configFile.size();
  std::unique_ptr<char[]> buf(new char[size]);
  configFile.readBytes(buf.get(), size);
  DynamicJsonBuffer jsonBuffer;
  JsonObject& json = jsonBuffer.parseObject(buf.get());
  if (json.success()) {
    logInfo("Parsed config json");
    return &json;
  } else {
    logInfo("Failed parsing config json");
  }
  return NULL;
}

