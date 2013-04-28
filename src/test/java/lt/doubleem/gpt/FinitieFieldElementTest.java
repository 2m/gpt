package lt.doubleem.gpt;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class FinitieFieldElementTest {
	
	@Test
	public void shouldPow() {
		FieldElement actual = new FiniteFieldElement(3, 2).pow(BigInteger.valueOf(2));
		FieldElement expected = new FiniteFieldElement(3, 1);
		assertEquals(expected, actual);
		
		actual = new FiniteFieldElement(9, 4).pow(BigInteger.valueOf(9));
		expected = new FiniteFieldElement(9, 1);
		assertEquals(expected, actual);
	}
}
