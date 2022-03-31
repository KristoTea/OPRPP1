package hr.fer.oprpp1.hw04.db.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LexerTest {
	
	@Test
	public void testEmptyQuery() {
		Lexer lexer = new Lexer(" ");
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testSimpleQuery() {
		Lexer lexer = new Lexer(" jmbag = \"0000000003\"");
		assertEquals(TokenType.KEYWORD, lexer.nextToken().getType());
		assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals(TokenType.LITERAL, lexer.nextToken().getType());
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testDoubleQuery() {
		Lexer lexer = new Lexer(" jmbag = \"0000000003\" anD lastName = \"blabla\" ");
		assertEquals(TokenType.KEYWORD, lexer.nextToken().getType());
		assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals(TokenType.LITERAL, lexer.nextToken().getType());
		assertEquals(TokenType.KEYWORD, lexer.nextToken().getType());
		assertEquals(TokenType.KEYWORD, lexer.nextToken().getType());
		assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals(TokenType.LITERAL, lexer.nextToken().getType());
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	

}
