package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	public void testHexToByte() {
		byte[] data = Util.hextobyte("01aE22");
		byte[] result = {1, -82, 34};
		
		assertEquals(result[0], data[0]);
		assertEquals(result[1], data[1]);
		assertEquals(result[2], data[2]);
	}
	
	@Test
	public void testHexToByteWrongSizedArgument() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01aE222"));
	}
	
	@Test
	public void testHexToByteEmptyArgument() {
		assertEquals(0, Util.hextobyte("").length);
	}
	
	@Test
	public void testHexToByteNoHexDigit() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("qwertzuiop"));
	}
	
	
	@Test
	public void testByteToHex() {
		String str = Util.bytetohex(new byte[] {1, -82, 34});
		String result = "01ae22";
		
		assertEquals(result, str);
	}
	
	

}
