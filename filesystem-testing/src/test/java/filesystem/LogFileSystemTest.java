package filesystem;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.github.sbridges.ephemeralfs.EphemeralFsFileSystemBuilder;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.collection.IsMapContaining.hasValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LogFileSystemTest {

	private FileSystem fileSystem;
	private LogFileSystem logFileSystem;

	@Before
	public void setup() throws IOException {
		fileSystem = EphemeralFsFileSystemBuilder
				.unixFs()
				.build();
		Path logDir = fileSystem.getPath("logs");
		Files.createDirectory(logDir);
		Files.write(logDir.resolve("my_log.log"), asList("line 1", "line 2"));
		Files.write(logDir.resolve("test.log"), asList("line 3", "line 4"));
		logFileSystem = new LogFileSystem(fileSystem);
	}

	@Test
	public void test() throws IOException {
		Map<String, String> result = logFileSystem.getLogFiles();
		assertNotNull(result);
		assertThat(result, hasKey("/logs/my_log.log"));
		assertThat(result, hasKey("/logs/test.log"));
		assertThat(result, hasValue("line 1, line 2"));
		assertThat(result, hasValue("line 3, line 4"));
	}
}