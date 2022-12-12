package fingerorder.webapp.utils;

import java.security.MessageDigest;

public class SHA256Utils {
	public static String encrypt(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(s.getBytes());

			return byteToHex(md.digest());
		} catch (Exception e) {
			return s;
		}
	}

	public static String byteToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bytes) {
			builder.append(b);
		}

		return builder.toString();
	}
}
