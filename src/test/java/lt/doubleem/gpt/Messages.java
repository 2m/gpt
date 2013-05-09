package lt.doubleem.gpt;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

public class Messages {
	
	public static Random r = new Random();

	public static Matrix<NonPrimeFieldElement> getAnyMessage(BigInteger p, BigInteger m, BigInteger N, int n) {
		Matrix<NonPrimeFieldElement> message = new Matrix<>(1, n);
		for (int i = 0; i < n; i++) {
			message.set(0, i, new NonPrimeFieldElement(p, m, N, r.nextInt(p.pow(m.multiply(N).intValue()).intValue() - 1)));
		}
		return message;
	}
	
	@Test
	public void shouldReturnMessage() {
		r = new Random(123);
		
		BigInteger p = BigInteger.valueOf(2);
		BigInteger m = BigInteger.valueOf(1);
		BigInteger N = BigInteger.valueOf(4);
		
		int n = 2;
		
		Matrix<NonPrimeFieldElement> m1 = getAnyMessage(p, m, N, n);
		assertEquals(new NonPrimeFieldElement(p, m, N, 2), m1.get(0, 0));
		assertEquals(new NonPrimeFieldElement(p, m, N, 5), m1.get(0, 1));
		
		m1 = getAnyMessage(p, m, N, n);
		assertEquals(new NonPrimeFieldElement(p, m, N, 11), m1.get(0, 0));
		assertEquals(new NonPrimeFieldElement(p, m, N, 14), m1.get(0, 1));
	}
}
