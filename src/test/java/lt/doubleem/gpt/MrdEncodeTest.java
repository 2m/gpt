package lt.doubleem.gpt;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class MrdEncodeTest {
	@Test
	public void calcGeneratorMatrix() {
		BigInteger p = BigInteger.valueOf(2);
		BigInteger m = BigInteger.valueOf(1);
		BigInteger N = BigInteger.valueOf(4);
		
		int n = 4; // code word length <= N
		int k = 2; // code dimension
		int d = n - k + 1; // code distance
		
		Matrix<NonPrimeFieldElement> gMatrix = getGeneratorMatrix(p, m, N, n, k);
		Matrix<NonPrimeFieldElement> hMatrix = gMatrix.dual();
		
		assertTrue(hMatrix.getRows() == d - 1);
		assertTrue(hMatrix.getCols() == n);
		assertTrue(gMatrix.mul(hMatrix.transpose()).isZero());
		
		List<Matrix<NonPrimeFieldElement>> messages = new ArrayList<>();
		for (int i = 0; i < p.pow(k).intValue(); i++) {
			Matrix<NonPrimeFieldElement> message = new Matrix<>(1, k);
			for (int j = 0; j < k; j++) {
				message.set(0, j, new NonPrimeFieldElement(p, m, N, BigInteger.valueOf(i).divide(p.pow(j)).mod(p).toString()));
			}
			messages.add(message);
		}
		
		List<Matrix<NonPrimeFieldElement>> codeWords = new ArrayList<>();
		for (Matrix<NonPrimeFieldElement> message: messages) {
			codeWords.add(message.mul(gMatrix));
		}
		
		for (Matrix<NonPrimeFieldElement> codeWord: codeWords) {
			assertTrue(hMatrix.mul(codeWord.transpose()).isZero());
			assertTrue(codeWord.mul(hMatrix.transpose()).isZero());
		}
		
		for (Matrix<NonPrimeFieldElement> codeWord: codeWords) {
			assertTrue(messages.contains(gMatrix.transpose().appendColumn(codeWord).getMatrix(k, k + 1, 0, 0).standardize().getColumn(k).transpose()));
		}
	}
	
	protected static Matrix<NonPrimeFieldElement> getGeneratorMatrix(BigInteger p, BigInteger m, BigInteger N, int n, int k) {
		List<NonPrimeFieldElement> gList = new ArrayList<>();
		gList.add(new NonPrimeFieldElement(p, m, N, BigInteger.valueOf(0)));
		gList.add(new NonPrimeFieldElement(p, m, N, BigInteger.valueOf(1)));
		gList.add(new NonPrimeFieldElement(p, m, N, BigInteger.valueOf(2)));
		gList.add(new NonPrimeFieldElement(p, m, N, BigInteger.valueOf(3)));
		
		Matrix<NonPrimeFieldElement> gMatrix = new Matrix<>(k, n);
		for (int row = 0; row < gMatrix.getRows(); row++) {
			for (int col = 0; col < gMatrix.getCols(); col++) {
				gMatrix.set(row, col, (NonPrimeFieldElement) gList.get(col).pow(p.pow(m.intValue()).pow(row)));
			}
		}
		
		return gMatrix;
	}
}
