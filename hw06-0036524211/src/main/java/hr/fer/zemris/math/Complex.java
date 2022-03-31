package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents complex number.
 * @author teakr
 *
 */
public class Complex {
	
	/**
	 * Private double variable represents real part of complex number.
	 */
	private double re;
	
	/**
	 * Private double variable represents imaginary part of complex number.
	 */
	private double im;
	
	
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Empty constructor. Returns 0+i0.
	 */
	public Complex() {
		this(0,0);
	}
	
	/**
	 * Constructor.
	 * @param re real part of complex number.
	 * @param im imaginary part of complex number.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Getter for real part of complex number.
	 * @return
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Getter for imaginary part of complex number.
	 * @return
	 */
	public double getIm() {
		return im;
	}

	// returns module of complex number
	/**
	 * Returns module of complex number.
	 * @return
	 */
	public double module() {
		return Math.hypot(re, im);
	}
	
	// returns this*c
	/**
	 * Calculate multiplication of two complex numbers.
	 * @param c complex number.
	 * @return result of multiplication.
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.re*c.re - this.im*c.im, this.re*c.im + this.im*c.re);
	}
	
	// returns this/c
	/**
	 * Calculate division of two complex numbers.
	 * @param c complex number.
	 * @return result of division.
	 */
	public Complex divide(Complex c) {
		Complex sConjgugated = new Complex(c.re, -c.im);
		Complex multiplication = this.multiply(sConjgugated);
		double squeredModule = c.module()*c.module();
		return new Complex(multiplication.getRe()/squeredModule, multiplication.getIm()/squeredModule);
	}
	
	// returns this+c
	/**
	 * Calculate addition of two complex numbers.
	 * @param c complex number.
	 * @return result of addition.
	 */
	public Complex add(Complex c) {
		return new Complex(this.re+c.re, this.im+c.im);
	}
	
	// returns this-c
	/**
	 * Calculate subtraction of two complex numbers.
	 * @param c complex number.
	 * @return result of subtraction.
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re-c.re, this.im-c.im);
	}
	
	// returns -this
	/**
	 * Method returns new complex number which is negation of this complex number.
	 * @return result of negating this complex number.
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}
	
	// returns this^n, n is non-negative integer
	/**
	 * Method returns power of n on this complex number.
	 * @param n power.
	 * @return result of operation power.
	 * @throws IllegalArgumentException if n is non-negative integer.
	 */
	public Complex power(int n) {
		if(n<0)
			throw new IllegalArgumentException("n must be non-negative integer.");
		
//		Complex result = this;
//		
//		for(int i = 1; i < n; i++) {
//			result.multiply(this);
//		}
		
		double r = this.module();
		double angle = Math.atan2(this.im, this.re);
		
		return transformInPolarForm(Math.pow(r, n), angle*n);
	}
	
	
	// returns n-th root of this, n is positive integer
	/**
	 * Method returns n-th root of this complex number.
	 * @param n.
	 * @return result of operation root.
	 * @throws IllegalArgumentException if n is not positive integer.
	 */
	public List<Complex> root(int n) {
		if(n<1)
			throw new IllegalArgumentException("n must be positive integer");
		
		List<Complex> roots = new ArrayList<>();
		double r = this.module();
		double angle = Math.atan2(this.im, this.re);
		
		for(int i = 0; i < n; i++) {
			roots.add(transformInPolarForm(Math.pow(r, 1.0/n), (angle + 2*i*Math.PI)/n));
		}
		
		return roots;
	}
	
	/**
	 * Helper method returns complex number in polar form. 
	 * @param r module.
	 * @param angle angle between imaginary and real part.
	 * @return complex number in polar form. 
	 */
	private Complex transformInPolarForm(double r, double angle) {
		//double r = c.module();
		//double angle = Math.atan2(c.im, c.re);
		return new Complex(r*Math.cos(angle), r*Math.sin(angle));
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(this.re);
		if(this.im >= 0) {
			sb.append("+");
			sb.append("i");
			sb.append(Math.abs(this.im));
		}else {
			sb.append("-");
			sb.append("i");
			sb.append(Math.abs(this.im));
		}
		sb.append(")");
		return sb.toString();
	}
	
	
	/**
	 * Method parse complex number from string.
	 * @param line string representation of complex number.
	 * @return complex number.
	 */
	public static Complex parse(String line) {
		if(line.isEmpty()) 
			throw new IllegalArgumentException("Input is empty.");
		
		double real = 0.0;
		double imaginary = 0.0;
		
		if(line.startsWith("+"))
			line = line.substring(1);
		
		line = line.replaceAll(" ", "");
		
		if(line.contains("+")) {
			real = Double.parseDouble(line.substring(0, line.indexOf('+')));
			String help = line.substring(line.indexOf('+')+1, line.length()).replace("i", "");
			if(help.isEmpty()) {
				imaginary = 1.0;
			}else {
				imaginary = Double.parseDouble(help);
			}	
		}else if(line.lastIndexOf('-') > 0) {
			real = Double.parseDouble(line.substring(0, line.indexOf('-')));
			String help = line.substring(line.indexOf('-')+1, line.length()).replace("i", "");
			if(help.isEmpty()) {
				imaginary = -1.0;
			}else {
				imaginary = -1.0*Double.parseDouble(help);
			}
		}else {
			if(line.contains("i")) {
				switch(line) {
					case "i":
						imaginary = 1.0;
						break;
					case "-i":
						imaginary = -1.0;
						break;
					default:
						imaginary = Double.parseDouble(line.replace("i", ""));
				}
			}else {
				real = Double.parseDouble(line);
			}
		}

		return new Complex(real, imaginary);
	}


}
