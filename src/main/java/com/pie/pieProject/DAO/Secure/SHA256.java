package com.pie.pieProject.DAO.Secure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SHA256 {
	public SHA256() {
	}

	public static String createSalt(String plainText) throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);

		String salt = new String(Base64.getEncoder().encode(bytes));

		return salt;
	}

	public static String encrypt(String plainText, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String rawAndSalt = plainText + salt;

			md.update(rawAndSalt.getBytes());
			byte[] byteData = md.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; ++i) {
				String hex = Integer.toHexString(255 & byteData[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
