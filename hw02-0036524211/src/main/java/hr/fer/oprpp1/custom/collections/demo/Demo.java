package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.*;

public class Demo {

	public static void main(String[] args) {
//		Collection col = new ArrayIndexedCollection(); 
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter getter = col.createElementsGetter();
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
		
//		Collection col = new ArrayIndexedCollection(); 
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter getter = col.createElementsGetter();
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		
//		Collection col = new ArrayIndexedCollection(); 
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter getter = col.createElementsGetter();
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());


		
//		Collection col = new ArrayIndexedCollection(); 
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter getter1 = col.createElementsGetter();
//		ElementsGetter getter2 = col.createElementsGetter();
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter2.getNextElement());
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter2.getNextElement());

		
//		Collection col1 = new ArrayIndexedCollection();
//		Collection col2 = new ArrayIndexedCollection();
//		col1.add("Ivo");
//		col1.add("Ana");
//		col1.add("Jasna");
//		col2.add("Jasmina");
//		col2.add("Štefanija");
//		col2.add("Karmela");
//		ElementsGetter getter1 = col1.createElementsGetter();
//		ElementsGetter getter2 = col1.createElementsGetter();
//		ElementsGetter getter3 = col2.createElementsGetter();
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter2.getNextElement());
//		System.out.println("Jedan element: " + getter3.getNextElement());
//		System.out.println("Jedan element: " + getter3.getNextElement());

//		Collection col = new ArrayIndexedCollection();
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter getter = col.createElementsGetter();
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		col.clear();
//		System.out.println("Jedan element: " + getter.getNextElement());
		
//		Collection col = new LinkedListIndexedCollection();
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter getter = col.createElementsGetter();
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		col.clear();
//		System.out.println("Jedan element: " + getter.getNextElement());
		
//		Collection col = new ArrayIndexedCollection();
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		ElementsGetter getter = col.createElementsGetter();
//		getter.getNextElement();
//		getter.processRemaining(System.out::println);
		
//		Tester t = new EvenIntegerTester();
//		System.out.println(t.test("Ivo"));
//		System.out.println(t.test(22));
//		System.out.println(t.test(3));
		
//		Collection col1 = new LinkedListIndexedCollection();
//		Collection col2 = new ArrayIndexedCollection();
//		col1.add(2);
//		col1.add(3);
//		col1.add(4);
//		col1.add(5);
//		col1.add(6);
//		col2.add(12);
//		col2.addAllSatisfying(col1, new EvenIntegerTester());
//		col2.forEach(System.out::println);
		
		List col1 = new ArrayIndexedCollection();
		List col2 = new LinkedListIndexedCollection();
		col1.add("Ivana");
		col2.add("Jasna");
		Collection col3 = col1;
		Collection col4 = col2;
		col1.get(0);
		col2.get(0);
		//col3.get(0); // neće se prevesti! Razumijete li zašto?
		//col4.get(0); // neće se prevesti! Razumijete li zašto?
		col1.forEach(System.out::println); // Ivana
		col2.forEach(System.out::println); // Jasna
		col3.forEach(System.out::println); // Ivana
		col4.forEach(System.out::println); // Jasna

	}

}
