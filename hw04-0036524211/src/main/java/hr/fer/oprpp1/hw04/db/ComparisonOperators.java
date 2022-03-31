package hr.fer.oprpp1.hw04.db;

/**
 * Razred koji nudi različite implementacije IComparisonOperator.
 * @author teakr
 *
 */
public class ComparisonOperators {
	
	public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;
	public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;
	public static final IComparisonOperator EQUALS = (v1, v2) -> v1.compareTo(v2) == 0;
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> v1.compareTo(v2) != 0;
	public static final IComparisonOperator LIKE = (v1, v2) -> {
		char[] c = v2.toCharArray();
		int noWildcard = 0;
		int wildcard = 0;
		for(int i = 0; i < c.length; i++) {
			if(c[i] == '*') {
				noWildcard++;
				wildcard = i;
			}
		}
		
		if(noWildcard != 1)
			throw new IllegalArgumentException("It's not possible to have more than 1 wildcard characters.");
		
		if(v1.length() < v2.length()-1)
			return false;
		
		return v1.startsWith(v2.substring(0, wildcard)) 
				&& v1.endsWith(v2.substring(wildcard+1, c.length));	
	};

}
