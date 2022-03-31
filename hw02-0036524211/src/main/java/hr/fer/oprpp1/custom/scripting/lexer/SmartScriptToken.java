package hr.fer.oprpp1.custom.scripting.lexer;

public class SmartScriptToken {
	
	private SmartScriptTokenType tokenType;
	private Object value;
	
	public SmartScriptToken(SmartScriptTokenType tokenType, Object value) {
		this.tokenType = tokenType;
		this.value = value;
	}
	
	public SmartScriptTokenType getTokenType() {
		return tokenType;
	}
	
	public Object getValue() {
		return value;
	}
	

}
