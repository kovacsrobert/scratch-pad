package filesystem;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.sbridges.ephemeralfs.EphemeralFsFileSystemBuilder;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
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
		logFileSystem = new LogFileSystem(fileSystem);
	}

	@Test
	public void test() throws IOException {
		Map<String, String> result = logFileSystem.getLogFiles();
		assertNotNull(result);
	}
}