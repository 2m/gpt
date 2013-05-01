package lt.doubleem.gpt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MrdEncodeTest {
	@Test
	public void calcGeneratorMatrix() {
		BigInteger p = BigInteger.valueOf(2);
		BigInteger m = BigInteger.valueOf(1);
		BigInteger N = BigInteger.valueOf(4);
		
		int d = 4;
		int n = 4;
		
		List<NonPrimeFieldElement> hList = new ArrayList<>();
		hList.add(new NonPrimeFieldElement(p, m, N, BigInteger.valueOf(0)));
		hList.add(new NonPrimeFieldElement(p, m, N, BigInteger.valueOf(1)));
		hList.add(new NonPrimeFieldElement(p, m, N, BigInteger.valueOf(2)));
		hList.add(new NonPrimeFieldElement(p, m, N, BigInteger.valueOf(3)));
		
		Matrix<NonPrimeFieldElement> hMatrix = new Matrix<>(d - 2 + 1, n);
		for (int row = 0; row < d - 2 + 1; row++) {
			for (int col = 0; col < n; col++) {
				hMatrix.set(row, col, (NonPrimeFieldElement) hList.get(col).pow(p.pow(m.intValue()).pow(row)));
			}
		}
				
		System.out.println(hMatrix);
		System.out.println(hMatrix.dual());
	}
}
