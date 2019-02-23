package filesystem;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.joining;

public class LogFileSystem {

	private static final Logger logger = LoggerFactory.getLogger(LogFileSystem.class);

	private final FileSystem fileSystem;

	public LogFileSystem(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}

	public Map<String, String> getLogFiles() throws IOException {
		Map<String, String> result = new HashMap<>();
		try (Stream<Path> pathStream = Files.walk(fileSystem.getPath("/logs"))) {
			pathStream
					.filter(Files::isRegularFile)
					.forEach(path -> {
						try {
							result.put(path.toString(), Files.lines(path)
									.collect(joining(", ")));
						} catch (IOException e) {
							logger.error("cannot read file", e);
						}
					});
		}
		return result;
	}
}
