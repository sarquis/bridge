package br.com.sqs.bridge.util;

import java.security.SecureRandom;

public class BridgePasswordHandler {

    private static final int MINIMUM_LENGTH = 8;

    public static boolean isPasswordSecure(String pass) {
	return pass.length() >= MINIMUM_LENGTH && pass.matches(".*[a-zA-Z].*") && pass.matches(".*\\d.*");
    }

    public static String generateInitialPassword() {

	// Characters for the password
	String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
	String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String digitChars = "0123456789";
	String specialChars = "!@#$%&";

	// Combine all character sets
	String allChars = lowercaseChars + uppercaseChars + digitChars + specialChars;

	// Create a StringBuilder to build the password
	StringBuilder passwordBuilder = new StringBuilder();

	// Use SecureRandom for cryptographic security
	SecureRandom random = new SecureRandom();

	// Ensure at least one lowercase, one uppercase, and one digit character
	passwordBuilder.append(lowercaseChars.charAt(random.nextInt(lowercaseChars.length())));
	passwordBuilder.append(uppercaseChars.charAt(random.nextInt(uppercaseChars.length())));
	passwordBuilder.append(digitChars.charAt(random.nextInt(digitChars.length())));

	// Generate the rest of the password
	for (int i = 3; i < MINIMUM_LENGTH; i++) {
	    passwordBuilder.append(allChars.charAt(random.nextInt(allChars.length())));
	}

	// Shuffle the characters in the password for better randomness
	char[] passwordChars = passwordBuilder.toString().toCharArray();
	for (int i = passwordChars.length - 1; i > 0; i--) {
	    int index = random.nextInt(i + 1);
	    char temp = passwordChars[index];
	    passwordChars[index] = passwordChars[i];
	    passwordChars[i] = temp;
	}

	return new String(passwordChars);
    }
}
