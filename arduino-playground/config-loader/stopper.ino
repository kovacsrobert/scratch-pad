
void stop(const char* message) {
	logInfo(message);
	stop();
}

void stop() {
  logInfo("Stopping program..");
	while(1);
}
