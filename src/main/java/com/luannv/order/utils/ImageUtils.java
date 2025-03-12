package com.luannv.order.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageUtils {
	public static byte[] getImageBytes(String imageUrl) throws IOException {
		URL url = new URL(imageUrl);

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try (InputStream stream = url.openStream()) {
			byte[] buffer = new byte[4096];

			while (true) {
				int bytesRead = stream.read(buffer);
				if (bytesRead < 0) {
					break;
				}
				output.write(buffer, 0, bytesRead);
			}
		}
		return output.toByteArray();
	}
}
