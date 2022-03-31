package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QueryParserTest {
	
	@Test
	public void testDirectQuery() {
		QueryParser parser = new QueryParser(" jmbag = \"0000000003\"");
		assertEquals(true, parser.isDirectQuery());
		assertEquals(1, parser.getQuery().size());
	}
	
	@Test
	public void testLongQuery() {
		QueryParser parser = new QueryParser("jmbag = \"0000000003\" AND lastName LIKE \"B*\"");
		assertEquals(false, parser.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());
		assertEquals(2, parser.getQuery().size());
	}

}
