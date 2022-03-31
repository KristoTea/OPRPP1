package hr.fer.zemris.math;

/**
 * Class representation of complex polynomial.
 * @author teakr
 *
 */
public class ComplexPolynomial {
	
	/**
	 * Array represents factors in complex polynomial.
	 */
	private Complex[] factors;
	
	// constructor
	/**
	 * Constructor.
	 * @param factors
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.factors = factors;
	}
	
	// returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	/**
	 * Returns order of this polynomial.
	 * @return
	 */
	public short order() {
		return (short)(factors.length-1);
	}
	
	// computes a new polynomial this*p
	/**
	 * Computes multiplication of two polynomial.
	 * @param p polynomial.
	 * @return result of multiplication.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] result = new Complex[this.order()+p.order()+1];
		
		for(int i = 0; i <= this.order(); i++) {
			for(int j = 0; j <= p.order(); j++) {
				if(result[i+j] == null) {
					result[i+j] = this.factors[i].multiply(p.factors[j]);
				}else {
					result[i+j] = result[i+j].add(this.factors[i].multiply(p.factors[j]));
				}
			}
		}
		
		return new ComplexPolynomial(result);
	}
	
	// computes first derivative of this polynomial; for example, for
	// (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	/**
	 * Computes first derivative of this polynomial.
	 * @return complex polynomial result of derivation.
	 */
	public ComplexPolynomial derive() {
		Complex[] result = new Complex[this.order()];
		for(int i = 1; i < factors.length; i++) {
			result[i-1] = new Complex(factors[i].getRe()*i, factors[i].getIm()*i);
		}
		return new ComplexPolynomial(result);
	}
	
	// computes polynomial value at given point z
	/**
	 * Computes polynomial value at given point z.
	 * @param z complex number.
	 * @return complex number.
	 */
	public Complex apply(Complex z) {
		Complex result = factors[0];
		
		for(int i = 1; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = factors.length-1; i > 0; i--) {
			sb.append(factors[i]);
			sb.append("*z^");
			sb.append(i);
			sb.append("+");
		}
		sb.append(factors[0]);
		return sb.toString();
	}

}
