
#include <FS.h>

void configFS() {
  logInfo("mounting FS...");
  if (!SPIFFS.begin()) {
    stop("cannot mount FS");
  }
  logInfo("Mounted file system");
}

File* loadFile(const char* filePath) {
  if (SPIFFS.exists(filePath)) {
    File configFile = SPIFFS.open(filePath, "r");
    if (configFile) {
      logInfo("Config file loaded");
      return &configFile;
    } else {
      logInfo("Failed to load config file");
    }
  } else {
    logInfo("Config file does not exist");
  }
  return NULL;
}
