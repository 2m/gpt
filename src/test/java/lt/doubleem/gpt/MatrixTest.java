package lt.doubleem.gpt;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class MatrixTest {
	
	@Test
	public void shouldSolveLinearEquation_2_3() {
		Matrix<FiniteFieldElement> m = new Matrix<>(2, 3);
		
		m.set(0, 0, new FiniteFieldElement(11, 1));
		m.set(0, 1, new FiniteFieldElement(11, 2));
		m.set(0, 2, new FiniteFieldElement(11, 8));
		
		m.set(1, 0, new FiniteFieldElement(11, 2));
		m.set(1, 1, new FiniteFieldElement(11, -1));
		m.set(1, 2, new FiniteFieldElement(11, 1));
		
		m.standardize();
		List<FiniteFieldElement> result = m.getColumn(2);
		assertTrue(result.size() == 2);
		assertTrue(result.contains(new FiniteFieldElement(11, 2)));
		assertTrue(result.contains(new FiniteFieldElement(11, 3)));
	}
	
	@Test
	public void shouldSolveLinearEquation_3_4() {
		Matrix<FiniteFieldElement> m = new Matrix<>(3, 4);
		
		m.set(0, 0, new FiniteFieldElement(11, 2));
		m.set(0, 1, new FiniteFieldElement(11, 1));
		m.set(0, 2, new FiniteFieldElement(11, 3));
		m.set(0, 3, new FiniteFieldElement(11, 0));
		
		m.set(1, 0, new FiniteFieldElement(11, 3));
		m.set(1, 1, new FiniteFieldElement(11, -1));
		m.set(1, 2, new FiniteFieldElement(11, -1));
		m.set(1, 3, new FiniteFieldElement(11, 4));
		
		m.set(2, 0, new FiniteFieldElement(11, 1));
		m.set(2, 1, new FiniteFieldElement(11, 1));
		m.set(2, 2, new FiniteFieldElement(11, 1));
		m.set(2, 3, new FiniteFieldElement(11, 4));
		
		m.standardize();
		List<FiniteFieldElement> result = m.getColumn(3);
		assertTrue(result.size() == 3);
		assertTrue(result.contains(new FiniteFieldElement(11, 2)));
		assertTrue(result.contains(new FiniteFieldElement(11, 5)));
		assertTrue(result.contains(new FiniteFieldElement(11, -3)));
	}
	
	@Test
	public void shouldInverse() {
		Matrix<FiniteFieldElement> m = new Matrix<>(3, 3);
		
		m.set(0, 0, new FiniteFieldElement(11, 1));
		m.set(0, 1, new FiniteFieldElement(11, 3));
		m.set(0, 2, new FiniteFieldElement(11, 3));
		
		m.set(1, 0, new FiniteFieldElement(11, 1));
		m.set(1, 1, new FiniteFieldElement(11, 4));
		m.set(1, 2, new FiniteFieldElement(11, 3));
		
		m.set(2, 0, new FiniteFieldElement(11, 1));
		m.set(2, 1, new FiniteFieldElement(11, 3));
		m.set(2, 2, new FiniteFieldElement(11, 4));
		
		Matrix<FiniteFieldElement> inverse = new Matrix<>(3, 3);
		
		inverse.set(0, 0, new FiniteFieldElement(11, 7));
		inverse.set(0, 1, new FiniteFieldElement(11, -3));
		inverse.set(0, 2, new FiniteFieldElement(11, -3));
		
		inverse.set(1, 0, new FiniteFieldElement(11, -1));
		inverse.set(1, 1, new FiniteFieldElement(11, 1));
		inverse.set(1, 2, new FiniteFieldElement(11, 0));
		
		inverse.set(2, 0, new FiniteFieldElement(11, -1));
		inverse.set(2, 1, new FiniteFieldElement(11, 0));
		inverse.set(2, 2, new FiniteFieldElement(11, 1));
		
		assertTrue(inverse.equals(m.inv()));
	}
	
	@Test
	public void shouldMultiply() {
		Matrix<FiniteFieldElement> m1 = new Matrix<>(3, 3);		
		m1.set(0, 0, new FiniteFieldElement(11, 1));
		m1.set(0, 1, new FiniteFieldElement(11, 3));
		m1.set(0, 2, new FiniteFieldElement(11, 3));
		
		m1.set(1, 0, new FiniteFieldElement(11, 1));
		m1.set(1, 1, new FiniteFieldElement(11, 4));
		m1.set(1, 2, new FiniteFieldElement(11, 3));
		
		m1.set(2, 0, new FiniteFieldElement(11, 1));
		m1.set(2, 1, new FiniteFieldElement(11, 3));
		m1.set(2, 2, new FiniteFieldElement(11, 4));
		
		Matrix<FiniteFieldElement> m2 = new Matrix<>(3, 3);		
		m2.set(0, 0, new FiniteFieldElement(11, 7));
		m2.set(0, 1, new FiniteFieldElement(11, -3));
		m2.set(0, 2, new FiniteFieldElement(11, -3));
		
		m2.set(1, 0, new FiniteFieldElement(11, -1));
		m2.set(1, 1, new FiniteFieldElement(11, 1));
		m2.set(1, 2, new FiniteFieldElement(11, 0));
		
		m2.set(2, 0, new FiniteFieldElement(11, -1));
		m2.set(2, 1, new FiniteFieldElement(11, 0));
		m2.set(2, 2, new FiniteFieldElement(11, 1));
		
		Matrix<FiniteFieldElement> m3 = new Matrix<>(3, 3);		
		m3.set(0, 0, new FiniteFieldElement(11, 1));
		m3.set(0, 1, new FiniteFieldElement(11, 0));
		m3.set(0, 2, new FiniteFieldElement(11, 0));
		
		m3.set(1, 0, new FiniteFieldElement(11, 0));
		m3.set(1, 1, new FiniteFieldElement(11, 1));
		m3.set(1, 2, new FiniteFieldElement(11, 0));
		
		m3.set(2, 0, new FiniteFieldElement(11, 0));
		m3.set(2, 1, new FiniteFieldElement(11, 0));
		m3.set(2, 2, new FiniteFieldElement(11, 1));
		
		assertTrue(m3.equals(m1.mul(m2)));
	}
	
	@Test
	public void shouldMultiplyNonSquare() {
		Matrix<FiniteFieldElement> m1 = new Matrix<>(2, 3);		
		m1.set(0, 0, new FiniteFieldElement(157, 1));
		m1.set(0, 1, new FiniteFieldElement(157, 2));
		m1.set(0, 2, new FiniteFieldElement(157, 3));
		
		m1.set(1, 0, new FiniteFieldElement(157, 4));
		m1.set(1, 1, new FiniteFieldElement(157, 5));
		m1.set(1, 2, new FiniteFieldElement(157, 6));
		
		Matrix<FiniteFieldElement> m2 = new Matrix<>(3, 2);		
		m2.set(0, 0, new FiniteFieldElement(157, 7));
		m2.set(0, 1, new FiniteFieldElement(157, 8));
		
		m2.set(1, 0, new FiniteFieldElement(157, 9));
		m2.set(1, 1, new FiniteFieldElement(157, 10));
		
		m2.set(2, 0, new FiniteFieldElement(157, 11));
		m2.set(2, 1, new FiniteFieldElement(157, 12));
		
		Matrix<FiniteFieldElement> m3 = new Matrix<>(2, 2);		
		m3.set(0, 0, new FiniteFieldElement(157, 58));
		m3.set(0, 1, new FiniteFieldElement(157, 64));
		
		m3.set(1, 0, new FiniteFieldElement(157, 139));
		m3.set(1, 1, new FiniteFieldElement(157, 154));
		
		assertTrue(m3.equals(m1.mul(m2)));
	}
	
	@Test
	public void shouldTranspose() {
		Matrix<FiniteFieldElement> m1 = new Matrix<>(3, 3);		
		m1.set(0, 0, new FiniteFieldElement(11, 1));
		m1.set(0, 1, new FiniteFieldElement(11, 3));
		m1.set(0, 2, new FiniteFieldElement(11, 3));
		
		m1.set(1, 0, new FiniteFieldElement(11, 1));
		m1.set(1, 1, new FiniteFieldElement(11, 4));
		m1.set(1, 2, new FiniteFieldElement(11, 3));
		
		m1.set(2, 0, new FiniteFieldElement(11, 1));
		m1.set(2, 1, new FiniteFieldElement(11, 3));
		m1.set(2, 2, new FiniteFieldElement(11, 4));
		
		Matrix<FiniteFieldElement> m2 = new Matrix<>(3, 3);		
		m2.set(0, 0, new FiniteFieldElement(11, 1));
		m2.set(0, 1, new FiniteFieldElement(11, 1));
		m2.set(0, 2, new FiniteFieldElement(11, 1));
		
		m2.set(1, 0, new FiniteFieldElement(11, 3));
		m2.set(1, 1, new FiniteFieldElement(11, 4));
		m2.set(1, 2, new FiniteFieldElement(11, 3));
		
		m2.set(2, 0, new FiniteFieldElement(11, 3));
		m2.set(2, 1, new FiniteFieldElement(11, 3));
		m2.set(2, 2, new FiniteFieldElement(11, 4));
		
		assertTrue(m2.equals(m1.transpose()));
	}
	
	@Test
	public void shouldTransposeNonSquare() {
		Matrix<FiniteFieldElement> m1 = new Matrix<>(2, 4);		
		m1.set(0, 0, new FiniteFieldElement(11, 1));
		m1.set(0, 1, new FiniteFieldElement(11, 3));
		m1.set(0, 2, new FiniteFieldElement(11, 3));
		m1.set(0, 3, new FiniteFieldElement(11, 2));
		
		m1.set(1, 0, new FiniteFieldElement(11, 1));
		m1.set(1, 1, new FiniteFieldElement(11, 4));
		m1.set(1, 2, new FiniteFieldElement(11, 3));
		m1.set(1, 3, new FiniteFieldElement(11, 2));
		
		Matrix<FiniteFieldElement> m2 = new Matrix<>(4, 2);		
		m2.set(0, 0, new FiniteFieldElement(11, 1));
		m2.set(0, 1, new FiniteFieldElement(11, 1));
		
		m2.set(1, 0, new FiniteFieldElement(11, 3));
		m2.set(1, 1, new FiniteFieldElement(11, 4));
		
		m2.set(2, 0, new FiniteFieldElement(11, 3));
		m2.set(2, 1, new FiniteFieldElement(11, 3));
		
		m2.set(3, 0, new FiniteFieldElement(11, 2));
		m2.set(3, 1, new FiniteFieldElement(11, 2));
		
		assertTrue(m2.equals(m1.transpose()));
	}
}
