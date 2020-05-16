package com.example.zipdemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;

public class ZipUtil {

	private static final Logger logger = LogManager.getLogger(ZipUtil.class);

	public static void recursiveUnzip(File inputZipFile, File targetDirectory) {
		List<File> zips = new ArrayList<>();
		Tika tika = new Tika();
		try {
			// Open the zip file
			ZipFile zipFile = new ZipFile(inputZipFile);
			Enumeration<?> enu = zipFile.entries();
			while (enu.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) enu.nextElement();

				String name = zipEntry.getName();
				long size = zipEntry.getSize();
				long compressedSize = zipEntry.getCompressedSize();
				logger.info("name: {} | size: {} | compressed size: {}",
						name,
						Long.valueOf(size),
						Long.valueOf(compressedSize));

				// Do we need to create a directory ?
				File file = Paths.get(targetDirectory.toString(), name).toFile();
				if (name.endsWith("/")) {
					file.mkdirs();
					continue;
				}

				File parent = file.getParentFile();
				if (parent != null) {
					parent.mkdirs();
				}

				// Extract the file
				InputStream is = zipFile.getInputStream(zipEntry);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				int length;
				while ((length = is.read(bytes)) >= 0) {
					fos.write(bytes, 0, length);
				}
				is.close();
				fos.close();

				String detectedType = tika.detect(file);
				if ("application/zip".equals(detectedType)) {
					zips.add(file);
				}
			}

			if (!zips.isEmpty()) {
				for (File zip : zips) {
					File uniqueZipDirectory = makeUniqueZipDirectory(zip);
					recursiveUnzip(zip, uniqueZipDirectory);
					zip.delete();
				}
			}

			zipFile.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static File makeUniqueZipDirectory(File file) {
		String fileName = file.getAbsolutePath();
		if (fileName.endsWith(".zip")) {
			fileName = fileName.replace(".zip", "");
		}
		File defaultFile = new File(fileName);
		if (!defaultFile.exists()) {
			return defaultFile;
		}
		int i = 1;
		while (true) {
			File newFile = new File(fileName + "_" + i);
			if (!newFile.exists()) {
				newFile.mkdir();
				return newFile;
			}
			logger.debug("Tested unique name: {}", newFile.getAbsolutePath());
			i++;
		}
	}
}
