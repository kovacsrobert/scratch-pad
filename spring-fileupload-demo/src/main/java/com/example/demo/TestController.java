package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	private static final Logger logger = Logger.getLogger("TestController");

	@GetMapping("/download")
	public void download(
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		final File tempFile1 = createTempFile();
		final File tempFile2 = createTempFile();
		final String boundary = UUID.randomUUID().toString();
		response.setContentType("multipart/related");
		//response.setContentType("message/rfc822; type=text/plain; boundary=" + boundary);
		//response.addHeader("Content-Disposition", "attachment; filename=" + tempFile1.getName());
//		Files.copy(tempFile1.toPath(), response.getOutputStream());
//		response.getOutputStream().flush();

		try (OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream())) {
			writeFileToResponse(writer, tempFile1, boundary);
			writeFileToResponse(writer, tempFile2, boundary);
			writer.write("--" + boundary);
		}
	}

	private File createTempFile() throws IOException {
		File tempFile = File.createTempFile("download-test-", null);
		String content = UUID.randomUUID().toString();
		logger.info(tempFile.getName() + " - " + content);
		Files.write(tempFile.toPath(), content.getBytes());
		return tempFile;
	}

	private void writeFileToResponse(OutputStreamWriter writer, File file, String boundary) throws IOException {
		writer.write("--" + boundary);
		writer.write("\n");
		writer.write("Content-Type: application/octet-stream");
		writer.write("\n");
		writer.write("Content-Location: " + file.getName());
		writer.write("\n");
		writer.write("Content-Disposition: attachment; filename=" + file.getName());
		writer.write("\n");
		writer.write("Content-ID: " + file.getName());
		writer.write("\n");
		writer.write("\n");
		byte[] tempFile1ContentBytes = Files.readAllBytes(file.toPath());
		String tempFile1Content = new String(tempFile1ContentBytes);
		writer.write(tempFile1Content);
		writer.write("\n");
	}
}
