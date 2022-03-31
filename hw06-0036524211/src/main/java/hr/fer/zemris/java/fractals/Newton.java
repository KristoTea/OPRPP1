package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {
	
	private static ComplexRootedPolynomial crp;
	private static ComplexPolynomial cp;

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		List<Complex> lista = new ArrayList<>();
		try {
			String line = "";
			while(!line.equals("done") || (lista.size()<2 && line.equals("done"))) {
				if((lista.size()<2 && line.equals("done"))) {
					System.out.println("You need to enter at leat two roots.");
					line = "";
					continue;
				}
				System.out.print("Root "+ (lista.size()+1) + "> ");
				line = sc.nextLine();

				try {
					if(!line.equals("done"))
						lista.add(Complex.parse(line));
				}catch(Exception e) {
					System.out.println("Wrong input, try again.");
				}
			}
			Complex[] arrayLista = new Complex[lista.size()];
			lista.toArray(arrayLista);
			
			crp = new ComplexRootedPolynomial(Complex.ONE, arrayLista);
			//System.out.println(crp);
			cp = crp.toComplexPolynom();
			//System.out.println(cp);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		sc.close();
		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new MojProducer());	

	}
	
	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			int offset = 0;
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {
				//if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					
					Complex zn = new Complex(cre, cim);
					Complex znold;
					int iter = 0;
					do {
						Complex numerator = cp.apply(zn);
						Complex denominator = cp.derive().apply(zn);
						znold = new Complex(zn.getRe(), zn.getIm());
						zn = zn.sub(numerator.divide(denominator));
						iter++;
					} while((znold.sub(zn).module()) > 0.001 && iter < m);
					int index = crp.indexOfClosestRootFor(zn, 0.002);
					data[offset++] = (short)(index+1);
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(cp.order()+1), requestNo);
		}
	}

}
