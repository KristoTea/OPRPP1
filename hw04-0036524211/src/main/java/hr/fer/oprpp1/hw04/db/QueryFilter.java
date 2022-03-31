package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Razred QueryFilter implementira sučelje IFilter.
 * @author teakr
 *
 */
public class QueryFilter implements IFilter{
	
	/**
	 * Privatna lista izraza unutar upita.
	 */
	private List<ConditionalExpression> expression;

	/**
	 * Konstruktor.
	 * @param expression
	 */
	public QueryFilter(List<ConditionalExpression> expression) {
		super();
		this.expression = expression;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression e : expression) {
			if(!e.getOperator().satisfied(e.getFieldValue().get(record), e.getStringLiteral()))
				return false;
		}
		return true;
	}
	
	

}
