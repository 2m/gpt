package lt.doubleem.gpt;

import static org.junit.Assert.assertFalse;
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
		
		Matrix<FiniteFieldElement> m1 = new Matrix<>(1, 2);
		
		m1.set(0, 0, new FiniteFieldElement(11, 2));
		m1.set(0, 1, new FiniteFieldElement(11, 3));
		
		assertTrue(m.standardize().getColumn(2).transpose().equals(m1));
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
		
		Matrix<FiniteFieldElement> m1 = new Matrix<>(1, 3);
		
		m1.set(0, 0, new FiniteFieldElement(11, 2));
		m1.set(0, 1, new FiniteFieldElement(11, 5));
		m1.set(0, 2, new FiniteFieldElement(11, -3));
		
		assertTrue(m.standardize().getColumn(3).transpose().equals(m1));
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
		assertTrue(m.mul(inverse).isOne());
		assertTrue(inverse.mul(m).isOne());
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
	public void shouldMultiplyByNumber() {
		int q = 11;
		
		Matrix<FiniteFieldElement> m1 = new Matrix<>(3, 3);		
		m1.set(0, 0, new FiniteFieldElement(q, 1));
		m1.set(0, 1, new FiniteFieldElement(q, 3));
		m1.set(0, 2, new FiniteFieldElement(q, 3));
		
		m1.set(1, 0, new FiniteFieldElement(q, 1));
		m1.set(1, 1, new FiniteFieldElement(q, 4));
		m1.set(1, 2, new FiniteFieldElement(q, 3));
		
		m1.set(2, 0, new FiniteFieldElement(q, 1));
		m1.set(2, 1, new FiniteFieldElement(q, 3));
		m1.set(2, 2, new FiniteFieldElement(q, 4));
		
		Matrix<FiniteFieldElement> m2 = new Matrix<>(3, 3);		
		m2.set(0, 0, new FiniteFieldElement(q, 2));
		m2.set(0, 1, new FiniteFieldElement(q, 6));
		m2.set(0, 2, new FiniteFieldElement(q, 6));
		
		m2.set(1, 0, new FiniteFieldElement(q, 2));
		m2.set(1, 1, new FiniteFieldElement(q, 8));
		m2.set(1, 2, new FiniteFieldElement(q, 6));
		
		m2.set(2, 0, new FiniteFieldElement(q, 2));
		m2.set(2, 1, new FiniteFieldElement(q, 6));
		m2.set(2, 2, new FiniteFieldElement(q, 8));
		
		assertTrue(m2.equals(m1.mul(2)));
	}
	
	@Test
	public void shouldAddTwoMatrices() {
		int q = 11;
		
		Matrix<FiniteFieldElement> m1 = new Matrix<>(3, 3);		
		m1.set(0, 0, new FiniteFieldElement(q, 1));
		m1.set(0, 1, new FiniteFieldElement(q, 3));
		m1.set(0, 2, new FiniteFieldElement(q, 3));
		
		m1.set(1, 0, new FiniteFieldElement(q, 1));
		m1.set(1, 1, new FiniteFieldElement(q, 4));
		m1.set(1, 2, new FiniteFieldElement(q, 3));
		
		m1.set(2, 0, new FiniteFieldElement(q, 1));
		m1.set(2, 1, new FiniteFieldElement(q, 3));
		m1.set(2, 2, new FiniteFieldElement(q, 4));
		
		Matrix<FiniteFieldElement> m2 = new Matrix<>(3, 3);		
		m2.set(0, 0, new FiniteFieldElement(q, 2));
		m2.set(0, 1, new FiniteFieldElement(q, 6));
		m2.set(0, 2, new FiniteFieldElement(q, 6));
		
		m2.set(1, 0, new FiniteFieldElement(q, 2));
		m2.set(1, 1, new FiniteFieldElement(q, 8));
		m2.set(1, 2, new FiniteFieldElement(q, 6));
		
		m2.set(2, 0, new FiniteFieldElement(q, 2));
		m2.set(2, 1, new FiniteFieldElement(q, 6));
		m2.set(2, 2, new FiniteFieldElement(q, 8));
		
		Matrix<FiniteFieldElement> m3 = new Matrix<>(3, 3);		
		m3.set(0, 0, new FiniteFieldElement(q, 3));
		m3.set(0, 1, new FiniteFieldElement(q, 9));
		m3.set(0, 2, new FiniteFieldElement(q, 9));
		
		m3.set(1, 0, new FiniteFieldElement(q, 3));
		m3.set(1, 1, new FiniteFieldElement(q, 1));
		m3.set(1, 2, new FiniteFieldElement(q, 9));
		
		m3.set(2, 0, new FiniteFieldElement(q, 3));
		m3.set(2, 1, new FiniteFieldElement(q, 9));
		m3.set(2, 2, new FiniteFieldElement(q, 1));
		
		assertTrue(m3.equals(m1.add(m2)));
	}
	
	@Test
	public void shouldSubtractTwoMatrices() {
		int q = 10;
		
		Matrix<FiniteFieldElement> m1 = new Matrix<>(2, 3);		
		m1.set(0, 0, new FiniteFieldElement(q, 1));
		m1.set(0, 1, new FiniteFieldElement(q, 2));
		m1.set(0, 2, new FiniteFieldElement(q, 3));
		
		m1.set(1, 0, new FiniteFieldElement(q, 7));
		m1.set(1, 1, new FiniteFieldElement(q, 8));
		m1.set(1, 2, new FiniteFieldElement(q, 9));
		
		Matrix<FiniteFieldElement> m2 = new Matrix<>(2, 3);		
		m2.set(0, 0, new FiniteFieldElement(q, 5));
		m2.set(0, 1, new FiniteFieldElement(q, 6));
		m2.set(0, 2, new FiniteFieldElement(q, 7));
		
		m2.set(1, 0, new FiniteFieldElement(q, 3));
		m2.set(1, 1, new FiniteFieldElement(q, 4));
		m2.set(1, 2, new FiniteFieldElement(q, 5));
		
		Matrix<FiniteFieldElement> m3 = new Matrix<>(2, 3);		
		m3.set(0, 0, new FiniteFieldElement(q, 6));
		m3.set(0, 1, new FiniteFieldElement(q, 6));
		m3.set(0, 2, new FiniteFieldElement(q, 6));
		
		m3.set(1, 0, new FiniteFieldElement(q, 4));
		m3.set(1, 1, new FiniteFieldElement(q, 4));
		m3.set(1, 2, new FiniteFieldElement(q, 4));
		
		assertTrue(m3.equals(m1.sub(m2)));
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
	
	@Test
	public void shouldReturnDual_rows_2_cols_4_q_3() {
		int q = 3;
		
		Matrix<FiniteFieldElement> m1 = new Matrix<>(2, 4);
		m1.set(0, 0, new FiniteFieldElement(q, 2));
		m1.set(0, 1, new FiniteFieldElement(q, 1));
		m1.set(0, 2, new FiniteFieldElement(q, 0));
		m1.set(0, 3, new FiniteFieldElement(q, 2));
		
		m1.set(1, 0, new FiniteFieldElement(q, 1));
		m1.set(1, 1, new FiniteFieldElement(q, 1));
		m1.set(1, 2, new FiniteFieldElement(q, 2));
		m1.set(1, 3, new FiniteFieldElement(q, 0));
		
		Matrix<FiniteFieldElement> m2 = new Matrix<>(2, 4);
		m2.set(0, 0, new FiniteFieldElement(q, 2));
		m2.set(0, 1, new FiniteFieldElement(q, 2));
		m2.set(0, 2, new FiniteFieldElement(q, 1));
		m2.set(0, 3, new FiniteFieldElement(q, 0));
		
		m2.set(1, 0, new FiniteFieldElement(q, 1));
		m2.set(1, 1, new FiniteFieldElement(q, 2));
		m2.set(1, 2, new FiniteFieldElement(q, 0));
		m2.set(1, 3, new FiniteFieldElement(q, 1));
		
		assertTrue(m1.dual().equals(m2));
		assertTrue(m1.dual().dual().standardize().equals(m1.standardize()));
		assertTrue(m1.mul(m1.dual().transpose()).isZero());
	}
	
	@Test
	public void shouldReturnDual_rows_3_cols_5_q_5() {
		int q = 5;
		
		Matrix<FiniteFieldElement> m1 = new Matrix<>(3, 5);
		m1.set(0, 0, new FiniteFieldElement(q, 4));
		m1.set(0, 1, new FiniteFieldElement(q, 3));
		m1.set(0, 2, new FiniteFieldElement(q, 1));
		m1.set(0, 3, new FiniteFieldElement(q, 4));
		m1.set(0, 4, new FiniteFieldElement(q, 3));
		
		m1.set(1, 0, new FiniteFieldElement(q, 3));
		m1.set(1, 1, new FiniteFieldElement(q, 1));
		m1.set(1, 2, new FiniteFieldElement(q, 2));
		m1.set(1, 3, new FiniteFieldElement(q, 0));
		m1.set(1, 4, new FiniteFieldElement(q, 4));
		
		m1.set(2, 0, new FiniteFieldElement(q, 4));
		m1.set(2, 1, new FiniteFieldElement(q, 1));
		m1.set(2, 2, new FiniteFieldElement(q, 4));
		m1.set(2, 3, new FiniteFieldElement(q, 2));
		m1.set(2, 4, new FiniteFieldElement(q, 4));
		
		Matrix<FiniteFieldElement> m2 = new Matrix<>(2, 5);
		m2.set(0, 0, new FiniteFieldElement(q, 3));
		m2.set(0, 1, new FiniteFieldElement(q, 4));
		m2.set(0, 2, new FiniteFieldElement(q, 0));
		m2.set(0, 3, new FiniteFieldElement(q, 1));
		m2.set(0, 4, new FiniteFieldElement(q, 0));
		
		m2.set(1, 0, new FiniteFieldElement(q, 3));
		m2.set(1, 1, new FiniteFieldElement(q, 2));
		m2.set(1, 2, new FiniteFieldElement(q, 1));
		m2.set(1, 3, new FiniteFieldElement(q, 0));
		m2.set(1, 4, new FiniteFieldElement(q, 1));
		
		assertTrue(m1.dual().equals(m2));
		assertTrue(m1.dual().dual().standardize().equals(m1.standardize()));
		
		// when standardizing m1, we need to swap some columns,
		// therefore after standardization we get generator matrix of equivalent code
		assertTrue(m1.standardize().mul(m1.dual().transpose()).isZero());
		assertFalse(m1.mul(m1.dual().transpose()).isZero());
	}
	
	@Test
	public void shouldReturnSelfDual_rows_2_cols_4_q_5() {
		int q = 2;
		
		Matrix<FiniteFieldElement> m1 = new Matrix<>(2, 4);
		m1.set(0, 0, new FiniteFieldElement(q, 1));
		m1.set(0, 1, new FiniteFieldElement(q, 0));
		m1.set(0, 2, new FiniteFieldElement(q, 1));
		m1.set(0, 3, new FiniteFieldElement(q, 0));
		
		m1.set(1, 0, new FiniteFieldElement(q, 0));
		m1.set(1, 1, new FiniteFieldElement(q, 1));
		m1.set(1, 2, new FiniteFieldElement(q, 0));
		m1.set(1, 3, new FiniteFieldElement(q, 1));
		
		assertTrue(m1.mul(m1.transpose()).isZero());
		assertTrue(m1.dual().equals(m1));		
		assertTrue(m1.mul(m1.dual().transpose()).isZero());		
	}
	
	@Test
	public void shouldStandardize_3_5_5() {
		int q = 5;
		
		Matrix<FiniteFieldElement> m1 = new Matrix<>(3, 5);
		m1.set(0, 0, new FiniteFieldElement(q, 4));
		m1.set(0, 1, new FiniteFieldElement(q, 3));
		m1.set(0, 2, new FiniteFieldElement(q, 1));
		m1.set(0, 3, new FiniteFieldElement(q, 4));
		m1.set(0, 4, new FiniteFieldElement(q, 3));
		
		m1.set(1, 0, new FiniteFieldElement(q, 3));
		m1.set(1, 1, new FiniteFieldElement(q, 1));
		m1.set(1, 2, new FiniteFieldElement(q, 2));
		m1.set(1, 3, new FiniteFieldElement(q, 0));
		m1.set(1, 4, new FiniteFieldElement(q, 4));
		
		m1.set(2, 0, new FiniteFieldElement(q, 4));
		m1.set(2, 1, new FiniteFieldElement(q, 1));
		m1.set(2, 2, new FiniteFieldElement(q, 4));
		m1.set(2, 3, new FiniteFieldElement(q, 2));
		m1.set(2, 4, new FiniteFieldElement(q, 4));
		
		Matrix<FiniteFieldElement> m2 = new Matrix<>(3, 5);
		m2.set(0, 0, new FiniteFieldElement(q, 1));
		m2.set(0, 1, new FiniteFieldElement(q, 0));
		m2.set(0, 2, new FiniteFieldElement(q, 0));
		m2.set(0, 3, new FiniteFieldElement(q, 2));
		m2.set(0, 4, new FiniteFieldElement(q, 2));
		
		m2.set(1, 0, new FiniteFieldElement(q, 0));
		m2.set(1, 1, new FiniteFieldElement(q, 1));
		m2.set(1, 2, new FiniteFieldElement(q, 0));
		m2.set(1, 3, new FiniteFieldElement(q, 1));
		m2.set(1, 4, new FiniteFieldElement(q, 3));
		
		m2.set(2, 0, new FiniteFieldElement(q, 0));
		m2.set(2, 1, new FiniteFieldElement(q, 0));
		m2.set(2, 2, new FiniteFieldElement(q, 1));
		m2.set(2, 3, new FiniteFieldElement(q, 0));
		m2.set(2, 4, new FiniteFieldElement(q, 4));
		
		assertTrue(m1.standardize().equals(m2));
	}
	
	@Test
	public void shouldStandardize_rows_3_cols_5_p_3_q_2_n_1() {
		int p = 3;
		int q = 2;
		int n = 1;
		
		Matrix<NonPrimeFieldElement> m1 = new Matrix<>(3, 5);
		m1.set(0, 0, new NonPrimeFieldElement(p, q, n, 2));
		m1.set(0, 1, new NonPrimeFieldElement(p, q, n, 1));
		m1.set(0, 2, new NonPrimeFieldElement(p, q, n, 6));
		m1.set(0, 3, new NonPrimeFieldElement(p, q, n, 4));
		m1.set(0, 4, new NonPrimeFieldElement(p, q, n, 3));
		
		m1.set(1, 0, new NonPrimeFieldElement(p, q, n, 0));
		m1.set(1, 1, new NonPrimeFieldElement(p, q, n, 2));
		m1.set(1, 2, new NonPrimeFieldElement(p, q, n, 5));
		m1.set(1, 3, new NonPrimeFieldElement(p, q, n, 3));
		m1.set(1, 4, new NonPrimeFieldElement(p, q, n, 1));
		
		m1.set(2, 0, new NonPrimeFieldElement(p, q, n, 6));
		m1.set(2, 1, new NonPrimeFieldElement(p, q, n, 2));
		m1.set(2, 2, new NonPrimeFieldElement(p, q, n, 3));
		m1.set(2, 3, new NonPrimeFieldElement(p, q, n, 4));
		m1.set(2, 4, new NonPrimeFieldElement(p, q, n, 0));
		
		Matrix<NonPrimeFieldElement> m2 = new Matrix<>(3, 5);
		m2.set(0, 0, new NonPrimeFieldElement(p, q, n, 0));
		m2.set(0, 1, new NonPrimeFieldElement(p, q, n, -1));
		m2.set(0, 2, new NonPrimeFieldElement(p, q, n, -1));
		m2.set(0, 3, new NonPrimeFieldElement(p, q, n, 5));
		m2.set(0, 4, new NonPrimeFieldElement(p, q, n, 2));
		
		m2.set(1, 0, new NonPrimeFieldElement(p, q, n, -1));
		m2.set(1, 1, new NonPrimeFieldElement(p, q, n, 0));
		m2.set(1, 2, new NonPrimeFieldElement(p, q, n, -1));
		m2.set(1, 3, new NonPrimeFieldElement(p, q, n, 6));
		m2.set(1, 4, new NonPrimeFieldElement(p, q, n, 0));
		
		m2.set(2, 0, new NonPrimeFieldElement(p, q, n, -1));
		m2.set(2, 1, new NonPrimeFieldElement(p, q, n, -1));
		m2.set(2, 2, new NonPrimeFieldElement(p, q, n, 0));
		m2.set(2, 3, new NonPrimeFieldElement(p, q, n, 3));
		m2.set(2, 4, new NonPrimeFieldElement(p, q, n, 3));
		
		assertTrue(m1.standardize().equals(m2));
	}
	
	@Test
	public void shouldFindPseudoinverse_2_4() {
		int p = 11;
		
		Matrix<FiniteFieldElement> m = new Matrix<>(2, 4);
		
		m.set(0, 0, new FiniteFieldElement(p, 4));
		m.set(0, 1, new FiniteFieldElement(p, 6));
		m.set(0, 2, new FiniteFieldElement(p, 0));
		m.set(0, 3, new FiniteFieldElement(p, 1));
		
		m.set(1, 0, new FiniteFieldElement(p, 9));
		m.set(1, 1, new FiniteFieldElement(p, 2));
		m.set(1, 2, new FiniteFieldElement(p, 0));
		m.set(1, 3, new FiniteFieldElement(p, 1));
		
		Matrix<FiniteFieldElement> pseudoinv = m.pseudoinv();  
		assertTrue(m.mul(pseudoinv).isOne());
	}
	
	@Test
	public void shouldFindPseudoinverse_4_2() {
		int p = 11;
		
		Matrix<FiniteFieldElement> m = new Matrix<>(4, 2);
		
		m.set(0, 0, new FiniteFieldElement(p, 4));
		m.set(0, 1, new FiniteFieldElement(p, 9));
		
		m.set(1, 0, new FiniteFieldElement(p, 6));
		m.set(1, 1, new FiniteFieldElement(p, 2));
		
		m.set(2, 0, new FiniteFieldElement(p, 0));
		m.set(2, 1, new FiniteFieldElement(p, 0));
		
		m.set(3, 0, new FiniteFieldElement(p, 1));
		m.set(3, 1, new FiniteFieldElement(p, 1));
		
		Matrix<FiniteFieldElement> pseudoinv = m.pseudoinv();  
		assertTrue(pseudoinv.mul(m).isOne());
	}
}
