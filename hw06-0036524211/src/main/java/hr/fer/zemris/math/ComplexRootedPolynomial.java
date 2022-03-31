package hr.fer.zemris.math;

/**
 * Class representation of complex rooted polynomial.
 * @author teakr
 *
 */
public class ComplexRootedPolynomial {
	
	private Complex constant;
	private Complex[] roots;
	

	// constructor
	/**
	 * Constructor.
	 * @param constant.
	 * @param roots.
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	// computes polynomial value at given point z
	/**
	 * Computes polynomial value at given point z
	 * @param z complex number.
	 * @return complex number.
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for(int i = 0; i < roots.length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}
		return result;
	}
	
	// converts this representation to ComplexPolynomial type
	/**
	 * Converts this representation to ComplexPolynomial type.
	 * @return ComplexPolynomial representation of this polynomial.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial[] helper = new ComplexPolynomial[roots.length];
		ComplexPolynomial result = new ComplexPolynomial(this.constant.negate());
		
		for(int i = 0; i < roots.length; i++) {
			helper[i] = new ComplexPolynomial(Complex.ONE, roots[i]);
		}
		
		for(int i = 0; i < helper.length; i++) {
			result = result.multiply(helper[i]);
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant);
		for(int i = 0; i < roots.length; i++) {
			sb.append("*(z-");
			sb.append(roots[i]);
			sb.append(")");
		}
		return sb.toString();
	}
	
	// finds index of closest root for given complex number z that is within
	// treshold; if there is no such root, returns -1
	// first root has index 0, second index 1, etc
	/**
	 * Finds index of closest root for given complex number z that is within treshold.
	 * @param z complex number.
	 * @param treshold double.
	 * @return index of closest root if such root exist, -1 otherwise.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double closest = treshold;
		for(int i = 0; i < roots.length; i++) {
			if(z.sub(roots[i]).module() < closest) {
				index = i;
				closest = z.sub(roots[i]).module();
			}		
		}
		return index;
	}

}
