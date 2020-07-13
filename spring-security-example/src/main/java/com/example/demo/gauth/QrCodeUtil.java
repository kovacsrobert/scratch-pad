package com.example.demo.gauth;

import static com.google.zxing.BarcodeFormat.QR_CODE;
import static com.google.zxing.EncodeHintType.ERROR_CORRECTION;
import static com.google.zxing.EncodeHintType.MARGIN;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCodeUtil {

	public static void write(File qrFile, String data) throws IOException, WriterException {
		Map<EncodeHintType, Object> hintsMap = new HashMap<>();
		hintsMap.put(ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hintsMap.put(MARGIN, Integer.valueOf(0));
		createQR(data, qrFile.toPath(), hintsMap);
	}

	private static void createQR(String data, Path path, Map<EncodeHintType, Object> hintsMap) throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(data, QR_CODE, 250, 250, hintsMap);
		MatrixToImageWriter.writeToPath(matrix, "png", path);
	}
}
