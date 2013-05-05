package lt.doubleem.gpt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LinearizedPolynomialTest {
	
	@Test
	public void shouldMultiplyPolynomials() {
		BigInteger powerOfPrime = BigInteger.valueOf(3).pow(2);
		
		LinearizedPolynomial<FiniteFieldElement> poly1 = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "023");
		LinearizedPolynomial<FiniteFieldElement> poly2 = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "041");
		
		LinearizedPolynomial<FiniteFieldElement> actual = poly1.mul(poly2);
		LinearizedPolynomial<FiniteFieldElement> expected = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "00253");
		assertEquals(expected, actual);
		
		actual = poly2.mul(poly1);
		expected = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "0058");
		assertEquals(expected, actual);
		
		poly1 = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "023");
		poly2 = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "042");
		
		actual = poly1.mul(poly2);
		expected = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "00216");
		assertEquals(expected, actual);
		
		actual = poly2.mul(poly1);
		expected = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "0057");
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldDividePolynomials() {
		BigInteger powerOfPrime = BigInteger.valueOf(3).pow(2);
		
		LinearizedPolynomial<FiniteFieldElement> f1 = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "00253");
		LinearizedPolynomial<FiniteFieldElement> g1 = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "041");
		
		LinearizedPolynomial<FiniteFieldElement> actual = f1.div(g1);
		LinearizedPolynomial<FiniteFieldElement> expected = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "023");
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldDividePolynomialsWithNonPrimeFieldElementCoeficients() {
		BigInteger p = BigInteger.valueOf(2);
		BigInteger m = BigInteger.valueOf(1);
		BigInteger N = BigInteger.valueOf(4);
		
		BigInteger powerOfPrime = p.pow(m.multiply(N).intValue());
		
		ArrayList<NonPrimeFieldElement> fCoef = new ArrayList<>();
		fCoef.add(new NonPrimeFieldElement(p, m, N, -1));
		fCoef.add(new NonPrimeFieldElement(p, m, N, -1));
		fCoef.add(new NonPrimeFieldElement(p, m, N, 0));
		LinearizedPolynomial<NonPrimeFieldElement> f = new LinearizedPolynomial<NonPrimeFieldElement>(powerOfPrime, fCoef);
		
		ArrayList<NonPrimeFieldElement> gCoef = new ArrayList<>();
		gCoef.add(new NonPrimeFieldElement(p, m, N, 0));
		gCoef.add(new NonPrimeFieldElement(p, m, N, -1));
		LinearizedPolynomial<NonPrimeFieldElement> g = new LinearizedPolynomial<>(powerOfPrime, gCoef);
		
		LinearizedPolynomial<NonPrimeFieldElement> actual = f.div(g.trim());
		assertEquals(f, actual);
	}
	
	@Test
	public void shouldNotDividePolynomials() {
		BigInteger powerOfPrime = BigInteger.valueOf(3).pow(2);
		LinearizedPolynomial<FiniteFieldElement> f1 = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "0058");
		LinearizedPolynomial<FiniteFieldElement> g1 = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "023");
		 
		try {
			LinearizedPolynomial<FiniteFieldElement> actual = f1.div(g1);
			fail("ArithmeticException expected");
		} catch (ArithmeticException ex) {
			// ok
		}
	}
	
	@Test
	public void shouldReturnRoots() {
		BigInteger powerOfPrime = BigInteger.valueOf(2).pow(1);
		LinearizedPolynomial<FiniteFieldElement> f1 = new LinearizedPolynomial<>(powerOfPrime, new FiniteFieldElement(powerOfPrime), "011");
		
		List<FiniteFieldElement> roots = new ArrayList<>();
		roots.add(new FiniteFieldElement(powerOfPrime, 1));
		
		assertTrue(f1.getRoots().equals(roots));
	}
	
}
