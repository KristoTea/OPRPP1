package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw04.db.lexer.Lexer;
import hr.fer.oprpp1.hw04.db.lexer.Token;
import hr.fer.oprpp1.hw04.db.lexer.TokenType;
/**
 * Razred koji modelira parser.
 * @author teakr
 *
 */
public class QueryParser {
	
	/**
	 * Privatni lexer.
	 */
	private Lexer lexer;
	
	/**
	 * Privatna varijabla koja čuva podatak je li upit direktan ili ne.
	 */
	private boolean direct;
	
	/**
	 * Privatna varijabla koja predstavlja jmbag iz direktnog upita.
	 */
	private String directJmbag;
	
	/**
	 * Privatna lista svih izraza koji se pojavljuju u upitu.
	 */
	private List<ConditionalExpression> expressions;
	
	/**
	 * Konstruktor.
	 * @param query upit.
	 */
	public QueryParser(String query) {
		this.lexer = new Lexer(query);
		this.direct = false;
		this.directJmbag = null;
		this.expressions = new ArrayList<ConditionalExpression>();
		this.parse();
	}
	
	/**
	 * Metoda koja obavlja parsiranje.
	 */
	private void parse() {
		Token token = lexer.nextToken();
		
		while(token.getType() != TokenType.EOF) {
			IFieldValueGetter fieldValue;
			String stringLiteral;
			IComparisonOperator operator;
			
			if(token.getType() != TokenType.KEYWORD)
				throw new RuntimeException("Query is invalid. Try again!");
			
			String tokenValue = token.getValue();
			
			switch(tokenValue) {	
				case "firstName":
					fieldValue = FieldValueGetters.FIRST_NAME;
					break;
				case "lastName":
					fieldValue = FieldValueGetters.LAST_NAME;
					break;
				case "jmbag":
					fieldValue = FieldValueGetters.JMBAG;
					break;
				default:
					throw new RuntimeException("Unknown keyword: " + tokenValue);
			}
			
			token = lexer.nextToken();
			
			if(token.getType() != TokenType.OPERATOR) {
				if(!token.getValue().equals("LIKE"))
					throw new RuntimeException("Expected operator. ");
			}
			
			tokenValue = token.getValue();
			
			switch(tokenValue) {
				case "<":
					operator = ComparisonOperators.LESS;
					break;
				case "<=":
					operator = ComparisonOperators.LESS_OR_EQUALS;
					break;
				case ">":
					operator = ComparisonOperators.GREATER;
					break;
				case ">=":
					operator = ComparisonOperators.GREATER_OR_EQUALS;
					break;
				case "=":
					operator = ComparisonOperators.EQUALS;
					break;
				case "!=": 
					operator = ComparisonOperators.NOT_EQUALS;
					break;
				case "LIKE":
					operator = ComparisonOperators.LIKE;
					break;
				default:
					throw new RuntimeException("Unknown operator: " + tokenValue);
			}

			
			token = lexer.nextToken();
			
			if(token.getType() != TokenType.LITERAL)
				throw new RuntimeException("Expected string literal");
			
			stringLiteral = token.getValue();
			
			expressions.add(new ConditionalExpression(fieldValue, stringLiteral, operator));
			
			token = lexer.nextToken();
			
			if(expressions.size() == 1 && token.getType() == TokenType.EOF) {
				if(fieldValue == FieldValueGetters.JMBAG && operator == ComparisonOperators.EQUALS) {
					direct = true;
					directJmbag = stringLiteral;
				}
			}
			
			if(token.getType() == TokenType.EOF)
				break;
			
			if(token.getValue().toUpperCase().equals("AND")) 
				token = lexer.nextToken();
			
		}		
	}
	
	/**
	 * Metoda provjerava je li upit direktan.
	 * @return true ako je upit direktan, false inače.
	 */
	public boolean isDirectQuery() {
		return direct;
	}
	
	/**
	 * Metoda vraća jmbag iz direktnog upita.
	 * @throws IllegalStateException ako je metoda pozana and upitom koji nije direktan.
	 * @return directJmbag.
	 */
	public String getQueriedJMBAG() {
		if(!direct)
			throw new IllegalStateException("Query is not direct.");
		return directJmbag;
	}
	
	/**
	 * Metoda vraća listu svih izraza unutar upita.
	 * @return izrazi List<ConditionalExpression>.
	 */
	public List<ConditionalExpression> getQuery(){
		return expressions;
	}

}
