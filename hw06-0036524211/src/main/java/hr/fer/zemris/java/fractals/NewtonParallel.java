package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class NewtonParallel {
	
	private static ComplexRootedPolynomial crp;
	private static ComplexPolynomial cp;
	
	private static int N; //workers
	private static int K; //tracks-jobs

	public static void main(String[] args) {
		if(args.length > 0) {
			for(int i = 0; i < args.length; i++) {
				if(args[i].startsWith("--workers")) {
					N = Integer.parseInt(args[i].substring(args[i].indexOf('=') + 1));
				}else if(args[i].startsWith("-w")){
					N = Integer.parseInt(args[i+1]);
				}else if(args[i].startsWith("--tracks")){
					K = Integer.parseInt(args[i].substring(args[i].indexOf('=') + 1));
				}else if(args[i].startsWith("-t")){
					K = Integer.parseInt(args[i+1]);
				}else {
					throw new RuntimeException("Error while reading arguments.");
				}
			}
		}
		
		if(N == 0)
			N = Runtime.getRuntime().availableProcessors();
		
		if(K == 0)
			K = 4*Runtime.getRuntime().availableProcessors();
		
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
			cp = crp.toComplexPolynom();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		sc.close();
		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new MojProducer());	


	}
	
	
	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}
		
		@Override
		public void run() {
			
			for(int y = yMin; y <= yMax; y++) {
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
					data[y*height + x] = (short)(index+1);
				}
			}
			
		}
	}
	
	
	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			short[] data = new short[width * height];
			
			if(K < 1)
				K = 1;
			
			if(K > height)
				K = height;
			
			System.out.println("Broj dretvi: " + N);
			System.out.println("Broj poslova: " + K);
			
			int brojYPoTraci = height / K;
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[N];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < K; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i == K-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(cp.order()+1), requestNo);
		}
	}

}
