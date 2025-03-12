package com.luannv.order.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageUtils {
	public static byte[] getImageBytes(String imageUrl) throws IOException {
		URL url = new URL(imageUrl);
		try (InputStream in = url.openStream();
				 ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

			byte[] data = new byte[8192]; // Buffer 8KB
			int bytesRead;
			while ((bytesRead = in.read(data)) != -1) {
				buffer.write(data, 0, bytesRead);
			}
			return buffer.toByteArray();
		}
	}
}
