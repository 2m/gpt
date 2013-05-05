package lt.doubleem.gpt;

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
}
