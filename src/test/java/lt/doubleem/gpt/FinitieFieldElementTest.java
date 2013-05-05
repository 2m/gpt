package lt.doubleem.gpt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

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
	
	@Test
	public void shouldReturnPrimitiveElement_7() {
		shouldReturnPrimitiveElement(7);
	}
	
	@Test
	public void shouldReturnPrimitiveElement_11() {
		shouldReturnPrimitiveElement(11);
	}
	
	@Test
	public void shouldReturnPrimitiveElement_23() {
		shouldReturnPrimitiveElement(23);
	}
	
	@Test
	public void shouldReturnPrimitiveElement_919() {
		shouldReturnPrimitiveElement(919);
	}
	
	public void shouldReturnPrimitiveElement(int p) {		
		Set<FieldElement> allEmenets = new HashSet<>();
    	FieldElement element = new FiniteFieldElement(p, 1);
    	while (!element.isZero()) {
    		allEmenets.add(element);
    		element = element.add(1);
    	}
		
    	FieldElement primitiveElement = allEmenets.iterator().next().getPrimitiveElement();
		element = primitiveElement.getOne().mul(primitiveElement);
		Set<FieldElement> generatedEmenets = new HashSet<>();
		while (!element.isOne()) {
			generatedEmenets.add(element);
			element = element.mul(primitiveElement);
		}
		generatedEmenets.add(element); // add one
		
		assertTrue(generatedEmenets.equals(allEmenets));
	}
}
