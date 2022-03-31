package hr.fer.oprpp1.hw04.db;

/**
 * Razred koji modelira cjeloviti izraz podupita.
 * @author teakr
 *
 */
public class ConditionalExpression {
	
	private IFieldValueGetter fieldValue;
	private String stringLiteral;
	private IComparisonOperator operator;
	
	public ConditionalExpression(IFieldValueGetter fieldValue, String stringLiteral, IComparisonOperator operator) {
		super();
		this.fieldValue = fieldValue;
		this.stringLiteral = stringLiteral;
		this.operator = operator;
	}

	public IFieldValueGetter getFieldValue() {
		return fieldValue;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}

	public IComparisonOperator getOperator() {
		return operator;
	}

}
