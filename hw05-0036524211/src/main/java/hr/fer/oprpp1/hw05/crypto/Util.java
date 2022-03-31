package hr.fer.oprpp1.hw05.crypto;

/**
 * Helper class with static methods for converting hex characters in bytes and vice versa.
 * @author teakr
 *
 */
public class Util {
	
	/**
	 * Method converts string written with hex characters into byte array.
	 * @param keyText
	 * @return
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText.length()%2 != 0) 
			throw new IllegalArgumentException("Argument must be even-sized!");
		
		if(keyText.length() == 0) 
			return new byte[0];
		
		keyText = keyText.toLowerCase();
		if(!keyText.matches("[0-9a-f]+"))
			throw new IllegalArgumentException("Argument must contain only hex digits!");
		
		byte[] data = new byte[keyText.length()/2];
		
		for(int i = 0; i < keyText.length(); i+=2) {
			char first = keyText.charAt(i);
			char second = keyText.charAt(i+1);
			data[i/2] = (byte)((Character.digit(first, 16) << 4) + Character.digit(second, 16));
		}
		
		return data;
	}
	
	/**
	 * Method converts byte array into hex coded string.
	 * @param bytearray
	 * @return
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();
		for(byte b : bytearray) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

}
