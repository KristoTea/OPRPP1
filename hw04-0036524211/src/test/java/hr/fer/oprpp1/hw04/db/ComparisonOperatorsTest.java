package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {
	
	@Test
	public void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertEquals(true, oper.satisfied("Ana", "Jasna")); 
	}
	
	@Test
	public void testLessOrEquals() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Ana")); 
		assertEquals(true, oper.satisfied("Ana", "Jasna")); 
	}
	
	@Test
	public void testGreate() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testGreaterOrEquals() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Ana")); 
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Ana")); 
	}
	
	@Test
	public void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertEquals(false, oper.satisfied("Ana", "Ana")); 
	}
	
	@Test
	public void testLikeThrowsException() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("Ana", "*Ana*"));
	}
	
	@Test
	public void testLikeTrue() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("AAAA", "AA*AA")); 
	}
	
	@Test
	public void testLikeFalse() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("AAA", "AA*AA")); 
	}

}
