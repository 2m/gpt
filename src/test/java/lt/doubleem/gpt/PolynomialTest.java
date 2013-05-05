package lt.doubleem.gpt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PolynomialTest {
	
	@Test
	public void shouldReturnCorrectDegree() {
		int p = 7;
		
		assertTrue(new Polynomial<FiniteFieldElement>(new FiniteFieldElement(p), "001").deg() == 2);
		assertTrue(new Polynomial<FiniteFieldElement>(new FiniteFieldElement(p), "0010").deg() == 2);
		assertTrue(new Polynomial<FiniteFieldElement>(new FiniteFieldElement(p), "0010000").deg() == 2);
		
		assertTrue(new Polynomial<FiniteFieldElement>(new FiniteFieldElement(p), "1").deg() == 0);
		assertTrue(new Polynomial<FiniteFieldElement>(new FiniteFieldElement(p), "1000").deg() == 0);
		
		assertTrue(new Polynomial<FiniteFieldElement>(new FiniteFieldElement(p), "").deg() == -1);
		assertTrue(new Polynomial<FiniteFieldElement>(new FiniteFieldElement(p), "0").deg() == -1);
	}
	
	@Test
	public void shouldSetCoef() {
		int p = 7;
		
		Polynomial<FiniteFieldElement> actual = new Polynomial<>(new FiniteFieldElement(p), "001");
		actual.setCoef(0, new FiniteFieldElement(p, 1));
		Polynomial<FiniteFieldElement> expected = new Polynomial<>(new FiniteFieldElement(p), "101");
		assertEquals(expected, actual);
		
		actual = new Polynomial<>(new FiniteFieldElement(p), "001");
		actual.setCoef(2, new FiniteFieldElement(p, 2));
		expected = new Polynomial<>(new FiniteFieldElement(p), "002");
		assertEquals(expected, actual);
		
		actual = new Polynomial<>(new FiniteFieldElement(p), "001");
		actual.setCoef(5, new FiniteFieldElement(p, 2));
		expected = new Polynomial<>(new FiniteFieldElement(p), "001002");
		assertEquals(expected, actual);
		
		actual = new Polynomial<>(new FiniteFieldElement(p), "000");
		actual.setCoef(1, new FiniteFieldElement(p, 2));
		expected = new Polynomial<>(new FiniteFieldElement(p), "02");
		assertEquals(expected, actual);
		
		actual = new Polynomial<>(new FiniteFieldElement(p), "");
		actual.setCoef(2, new FiniteFieldElement(p, 2));
		expected = new Polynomial<>(new FiniteFieldElement(p), "002");
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldBeZero() {
		int p = 7;
		
		assertTrue(new Polynomial<FiniteFieldElement>(new FiniteFieldElement(p), "0").isZero());
		assertTrue(new Polynomial<FiniteFieldElement>(new FiniteFieldElement(p), "0000").isZero());
		assertTrue(new Polynomial<FiniteFieldElement>(new FiniteFieldElement(p), "").isZero());
	}
	
	@Test
	public void shouldBeEqual() {
		int p = 7;
		
		Polynomial<FiniteFieldElement> actual = new Polynomial<>(new FiniteFieldElement(p), "00100");
		Polynomial<FiniteFieldElement> expected = new Polynomial<>(new FiniteFieldElement(p), "001");
		assertEquals(expected, actual);
		assertEquals(expected.hashCode(), actual.hashCode());
		
		actual = new Polynomial<>(new FiniteFieldElement(p), "0000");
		expected = new Polynomial<>(new FiniteFieldElement(p), "00");
		assertEquals(expected, actual);
		assertEquals(expected.hashCode(), actual.hashCode());
		
		actual = new Polynomial<>(new FiniteFieldElement(p), "0");
		expected = new Polynomial<>(new FiniteFieldElement(p), "00");
		assertEquals(expected, actual);
		assertEquals(expected.hashCode(), actual.hashCode());
		
		actual = new Polynomial<>(new FiniteFieldElement(p), "00");
		expected = new Polynomial<>(new FiniteFieldElement(p), "");
		assertEquals(expected, actual);
		assertEquals(expected.hashCode(), actual.hashCode());
	}
}
