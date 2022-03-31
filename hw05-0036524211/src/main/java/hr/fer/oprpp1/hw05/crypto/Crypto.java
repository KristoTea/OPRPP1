package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class Crypto allows user to encrypt/decrypt 
 * given file using the AES crypto-algorithm and the 128-bit 
 * encryption key or calculate and check the SHA-256 file digest.
 * @author teakr
 *
 */
public class Crypto {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		if(args.length < 2)
			throw new IllegalArgumentException("There is not enough arguments!");
		
		String in = args[1];

		Scanner sc = new Scanner(System.in);
		
		switch(args[0]) {
		case "encrypt":
		case "decrypt":
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.printf("%s", ">");
			String keyText = sc.nextLine();
			
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.printf("%s", ">");
			String ivText = sc.nextLine();
			
			String out = args[2];
			
			if(args[0].equals("encrypt")) {
				crypt(keyText, ivText, Paths.get(in),Paths.get(out), true);
				System.out.println("Encryption completed. Generated file " + out + " based on file " + in +".");
			}else {
				crypt(keyText, ivText, Paths.get(in),Paths.get(out), false);
				System.out.println("Decryption completed. Generated file " + out + " based on file " + in +".");
			}
			break;
		case "calculatesha":
			byte[] digest = calculateSHA(Paths.get(in));
			System.out.println("Digesting completed. Digest of " + in + "is: " + Util.bytetohex(digest));
			break;
		case "checksha":
			System.out.println("Please provide expected sha-256 digest for " + in + ":");
			System.out.printf("%s", ">");
			
			String argumentSHA = sc.nextLine();
			if(checkSHA(Paths.get(in), argumentSHA)) {
				System.out.println("Digesting completed. Digest of " + in + " matches expected digest.");
			}else {
				System.out.println("Digesting completed. Digest of " + in + " does not match the expected digest. Digest was: " 
						+ Util.bytetohex(calculateSHA(Paths.get(in))));
			}
			break;
		default:
			sc.close();
			throw new IllegalArgumentException("Unknown argument: " + args[0]);
		}
		
		sc.close();

	}
	
	/**
	 * Private method encrypts given file using password and initialization vector..
	 * @param keyText
	 * @param ivText
	 * @param in
	 * @param out
	 * @param encrypt
	 */
	private static void crypt(String keyText, String ivText, Path in, Path out, boolean encrypt) {
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			try(InputStream is = Files.newInputStream(in); OutputStream op = Files.newOutputStream(out)){
				byte[] buf = new byte[4096];
				while(true) {
					int len = is.read(buf);
					if(len < 1)
						break;
					op.write(cipher.update(buf, 0, len));
				}
			}catch(IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch(NoSuchPaddingException e) {
			e.printStackTrace();
			System.exit(-1);
		}catch (InvalidKeyException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			System.exit(-1);
		} 
		
	}
	
	/**
	 * Private method calculates SHA.
	 * @param p path to the file.
	 * @return message digest.
	 * @throws NoSuchAlgorithmException
	 */
	private static byte[] calculateSHA(Path p) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		try(InputStream is = Files.newInputStream(p)){
			byte[] buf = new byte[4096];
			while(true) {
				int len = is.read(buf);
				if(len < 1)
					break;
				md.update(buf, 0, len);
			}
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return md.digest();
	}
	
	/**
	 * Private method checks if calculated SHA matches the one from the argument.
	 * @param p path to the file.
	 * @param argumentSHA
	 * @return true if calculated SHA is same as argumentSHA, false otherwise.
	 * @throws NoSuchAlgorithmException
	 */
	private static boolean checkSHA(Path p, String argumentSHA) throws NoSuchAlgorithmException {
		byte[] digest = calculateSHA(p); 	
		return argumentSHA.equals(Util.bytetohex(digest));
	}
	

}
