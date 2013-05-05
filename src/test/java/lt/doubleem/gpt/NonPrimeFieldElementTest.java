package lt.doubleem.gpt;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class NonPrimeFieldElementTest {
	
	@Test
	public void shouldCreate_2_3_1() {
		int p = 2;
		int m = 3;
		int n = 1;
		
		new NonPrimeFieldElement(p, m, n, -1);
		new NonPrimeFieldElement(p, m, n, 0);
		new NonPrimeFieldElement(p, m, n, 1);
		new NonPrimeFieldElement(p, m, n, 2);
		new NonPrimeFieldElement(p, m, n, 3);
		new NonPrimeFieldElement(p, m, n, 4);
		new NonPrimeFieldElement(p, m, n, 5);
		new NonPrimeFieldElement(p, m, n, 6);
	}
	
	@Test
	public void shouldAdd_2_3_1() {
		int p = 2;
		int m = 3;
		int n = 1;
		
		FieldElement f0 = new NonPrimeFieldElement(p, m, n, -1);
		FieldElement f1 = new NonPrimeFieldElement(p, m, n, 0);
		FieldElement f2 = new NonPrimeFieldElement(p, m, n, 1);
		FieldElement f3 = new NonPrimeFieldElement(p, m, n, 2);
		FieldElement f4 = new NonPrimeFieldElement(p, m, n, 3);
		FieldElement f5 = new NonPrimeFieldElement(p, m, n, 4);
		FieldElement f6 = new NonPrimeFieldElement(p, m, n, 5);
		FieldElement f7 = new NonPrimeFieldElement(p, m, n, 6);
		
		assertTrue(f1.add(f1).equals(f0));
		assertTrue(f1.add(f2).equals(f4));
		assertTrue(f1.add(f3).equals(f7));
		assertTrue(f1.add(f4).equals(f2));
		assertTrue(f1.add(f5).equals(f6));
		assertTrue(f1.add(f6).equals(f5));
		assertTrue(f1.add(f7).equals(f3));
		
		assertTrue(f2.add(f2).equals(f0));
		assertTrue(f2.add(f3).equals(f5));
		assertTrue(f2.add(f4).equals(f1));
		assertTrue(f2.add(f5).equals(f3));
		assertTrue(f2.add(f6).equals(f7));
		assertTrue(f2.add(f7).equals(f6));
		
		assertTrue(f5.add(f7).equals(f4));
	}
	
	@Test
	public void shouldSubtract_2_3_1() {
		int p = 2;
		int m = 3;
		int n = 1;
		
		FieldElement f0 = new NonPrimeFieldElement(p, m, n, -1);
		FieldElement f1 = new NonPrimeFieldElement(p, m, n, 0);
		FieldElement f2 = new NonPrimeFieldElement(p, m, n, 1);
		FieldElement f3 = new NonPrimeFieldElement(p, m, n, 2);
		FieldElement f4 = new NonPrimeFieldElement(p, m, n, 3);
		FieldElement f5 = new NonPrimeFieldElement(p, m, n, 4);
		FieldElement f6 = new NonPrimeFieldElement(p, m, n, 5);
		FieldElement f7 = new NonPrimeFieldElement(p, m, n, 6);
		
		assertTrue(f4.sub(f1).equals(f2));
		assertTrue(f4.sub(f2).equals(f1));
		assertTrue(f4.sub(f3).equals(f6));
		assertTrue(f4.sub(f4).equals(f0));
		assertTrue(f4.sub(f5).equals(f7));
		assertTrue(f4.sub(f6).equals(f3));
		assertTrue(f4.sub(f7).equals(f5));
	}
	
	@Test
	public void shouldMultiply_2_3_() {
		int p = 2;
		int m = 3;
		int n = 1;
		
		FieldElement f0 = new NonPrimeFieldElement(p, m, n, -1);
		FieldElement f1 = new NonPrimeFieldElement(p, m, n, 0);
		FieldElement f2 = new NonPrimeFieldElement(p, m, n, 1);
		FieldElement f3 = new NonPrimeFieldElement(p, m, n, 2);
		FieldElement f4 = new NonPrimeFieldElement(p, m, n, 3);
		FieldElement f5 = new NonPrimeFieldElement(p, m, n, 4);
		FieldElement f6 = new NonPrimeFieldElement(p, m, n, 5);
		FieldElement f7 = new NonPrimeFieldElement(p, m, n, 6);
		
		assertTrue(f7.mul(f0).equals(f0));
		assertTrue(f7.mul(f1).equals(f7));
		assertTrue(f7.mul(f2).equals(f1));
		assertTrue(f7.mul(f3).equals(f2));
		assertTrue(f7.mul(f4).equals(f3));
		assertTrue(f7.mul(f5).equals(f4));
		assertTrue(f7.mul(f6).equals(f5));
		assertTrue(f7.mul(f7).equals(f6));
	}
	
	@Test
	public void shouldMultiplyByNumber_2_4_1() {
		int p = 2;
		int m = 4;
		int n = 1;
		
		FieldElement f0 = new NonPrimeFieldElement(p, m, n, -1);		
		FieldElement f3 = new NonPrimeFieldElement(p, m, n, 2);		
		FieldElement f10 = new NonPrimeFieldElement(p, m, n, 9);
		
		assertTrue(f3.mul(-2).equals(f0));
		assertTrue(f3.mul(-1).equals(f3));
		assertTrue(f3.mul(0).equals(f0));
		assertTrue(f3.mul(1).equals(f3));
		assertTrue(f3.mul(2).equals(f0));
		assertTrue(f3.mul(3).equals(f3));
		
		assertTrue(f10.mul(-2).equals(f0));
		assertTrue(f10.mul(-1).equals(f10));
		assertTrue(f10.mul(0).equals(f0));
		assertTrue(f10.mul(1).equals(f10));
		assertTrue(f10.mul(2).equals(f0));
		assertTrue(f10.mul(3).equals(f10));
	}
	
	@Test
	public void shouldPower_2_3_1() {
		int p = 2;
		int m = 3;
		int n = 1;
		
		FieldElement f0 = new NonPrimeFieldElement(p, m, n, -1);
		FieldElement f1 = new NonPrimeFieldElement(p, m, n, 0);
		FieldElement f2 = new NonPrimeFieldElement(p, m, n, 1);
		FieldElement f3 = new NonPrimeFieldElement(p, m, n, 2);
		FieldElement f4 = new NonPrimeFieldElement(p, m, n, 3);
		FieldElement f5 = new NonPrimeFieldElement(p, m, n, 4);
		FieldElement f6 = new NonPrimeFieldElement(p, m, n, 5);
		FieldElement f7 = new NonPrimeFieldElement(p, m, n, 6);
		
		assertTrue(f3.pow(BigInteger.valueOf(2)).equals(f5));
		assertTrue(f3.pow(BigInteger.valueOf(3)).equals(f7));
		assertTrue(f3.pow(BigInteger.valueOf(4)).equals(f2));
		assertTrue(f3.pow(BigInteger.valueOf(5)).equals(f4));
		assertTrue(f3.pow(BigInteger.valueOf(6)).equals(f6));
		assertTrue(f3.pow(BigInteger.valueOf(7)).equals(f1));
		assertTrue(f3.pow(BigInteger.valueOf(8)).equals(f3));
	}
	
	@Test
	public void shouldPower_2_4_1() {
		int p = 2;
		int m = 4;
		int n = 1;
		
		FieldElement f0 = new NonPrimeFieldElement(p, m, n, -1);
		FieldElement f1 = new NonPrimeFieldElement(p, m, n, 0);
		FieldElement f2 = new NonPrimeFieldElement(p, m, n, 1);
		FieldElement f3 = new NonPrimeFieldElement(p, m, n, 2);
		FieldElement f4 = new NonPrimeFieldElement(p, m, n, 3);
		FieldElement f5 = new NonPrimeFieldElement(p, m, n, 4);
		FieldElement f6 = new NonPrimeFieldElement(p, m, n, 5);
		FieldElement f7 = new NonPrimeFieldElement(p, m, n, 6);
		FieldElement f8 = new NonPrimeFieldElement(p, m, n, 7);
		FieldElement f9 = new NonPrimeFieldElement(p, m, n, 8);
		FieldElement f10 = new NonPrimeFieldElement(p, m, n, 9);
		FieldElement f11 = new NonPrimeFieldElement(p, m, n, 10);
		FieldElement f12 = new NonPrimeFieldElement(p, m, n, 11);
		FieldElement f13 = new NonPrimeFieldElement(p, m, n, 12);
		FieldElement f14 = new NonPrimeFieldElement(p, m, n, 13);
		FieldElement f15 = new NonPrimeFieldElement(p, m, n, 14);
		
		assertTrue(f3.pow(BigInteger.valueOf(2)).equals(f5));
		assertTrue(f3.pow(BigInteger.valueOf(3)).equals(f7));
		assertTrue(f3.pow(BigInteger.valueOf(4)).equals(f9));
		assertTrue(f3.pow(BigInteger.valueOf(5)).equals(f11));
		assertTrue(f3.pow(BigInteger.valueOf(6)).equals(f13));
		assertTrue(f3.pow(BigInteger.valueOf(7)).equals(f15));
		assertTrue(f3.pow(BigInteger.valueOf(8)).equals(f2));
		assertTrue(f3.pow(BigInteger.valueOf(9)).equals(f4));
		assertTrue(f3.pow(BigInteger.valueOf(10)).equals(f6));
		assertTrue(f3.pow(BigInteger.valueOf(11)).equals(f8));
		assertTrue(f3.pow(BigInteger.valueOf(12)).equals(f10));
		assertTrue(f3.pow(BigInteger.valueOf(13)).equals(f12));
		assertTrue(f3.pow(BigInteger.valueOf(14)).equals(f14));
		assertTrue(f3.pow(BigInteger.valueOf(15)).equals(f1));
		assertTrue(f3.pow(BigInteger.valueOf(16)).equals(f3));
	}
	
	@Test
	public void shouldInverse_2_3_1() {
		int p = 2;
		int m = 3;
		int n = 1;
		
		FieldElement f0 = new NonPrimeFieldElement(p, m, n, -1);
		FieldElement f1 = new NonPrimeFieldElement(p, m, n, 0);
		FieldElement f2 = new NonPrimeFieldElement(p, m, n, 1);
		FieldElement f3 = new NonPrimeFieldElement(p, m, n, 2);
		FieldElement f4 = new NonPrimeFieldElement(p, m, n, 3);
		FieldElement f5 = new NonPrimeFieldElement(p, m, n, 4);
		FieldElement f6 = new NonPrimeFieldElement(p, m, n, 5);
		FieldElement f7 = new NonPrimeFieldElement(p, m, n, 6);
		
		assertTrue(f1.inv().equals(f1));
		assertTrue(f2.inv().equals(f7));
		assertTrue(f3.inv().equals(f6));
		assertTrue(f4.inv().equals(f5));
	}
	
	@Test
	public void shouldReturnPrimitiveElement_2_3_1() {
		int p = 2;
		int m = 3;
		int n = 1;
		
		Set<NonPrimeFieldElement> allEmenets = new HashSet<>();
		allEmenets.add(new NonPrimeFieldElement(p, m, n, 0));
		allEmenets.add(new NonPrimeFieldElement(p, m, n, 1));
		allEmenets.add(new NonPrimeFieldElement(p, m, n, 2));
		allEmenets.add(new NonPrimeFieldElement(p, m, n, 3));
		allEmenets.add(new NonPrimeFieldElement(p, m, n, 4));
		allEmenets.add(new NonPrimeFieldElement(p, m, n, 5));
		allEmenets.add(new NonPrimeFieldElement(p, m, n, 6));
		
		NonPrimeFieldElement primitiveElement = (NonPrimeFieldElement) allEmenets.iterator().next().getPrimitiveElement();
		NonPrimeFieldElement element = (NonPrimeFieldElement) primitiveElement.getOne().mul(primitiveElement);
		Set<NonPrimeFieldElement> generatedEmenets = new HashSet<>();
		while (!element.isOne()) {
			generatedEmenets.add(element);
			element = (NonPrimeFieldElement) element.mul(primitiveElement);
		}
		generatedEmenets.add(element); // add one
		
		assertTrue(generatedEmenets.equals(allEmenets));
	}
}
