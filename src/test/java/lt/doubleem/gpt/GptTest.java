package lt.doubleem.gpt;

import static lt.doubleem.gpt.MrdDecodeTest.calculateErrorVector;
import static lt.doubleem.gpt.MrdEncodeTest.getGeneratorMatrix;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

public class GptTest {
	
	@Test
	public void shouldCreatePrivateKey() {
		BigInteger p = BigInteger.valueOf(2);
		BigInteger m = BigInteger.valueOf(1);
		BigInteger N = BigInteger.valueOf(4);
		BigInteger q = p.pow(m.intValue());
		
		int n = 4; // code word length <= N
		int k = 2; // code dimension
		int d = n - k + 1; // code distance
		
		Matrix<NonPrimeFieldElement> gMatrix = getGeneratorMatrix(p, m, N, n, k);
		Matrix<NonPrimeFieldElement> hMatrix = gMatrix.dual();
		
		NonPrimeFieldElement u = new NonPrimeFieldElement(p, m, N, 1);
		Matrix<NonPrimeFieldElement> uMatrix = new Matrix<>(k, k);
		for (int row = 0; row < k; row++) {
			for (int col = 0; col < k; col++) {
				if (row == col) {
					uMatrix.set(row, col, (NonPrimeFieldElement) u.pow(q.pow(row)));
				}
				else {
					uMatrix.set(row, col, (NonPrimeFieldElement) u.getZero());
				}
			}
		}
		
		int l = 1;
		int t1 = 1;
		
		Matrix<NonPrimeFieldElement> pMatrix = new Matrix<>(n, n);
		for (int row = 0; row < pMatrix.getRows(); row++) {
			for (int col = 0; col < pMatrix.getCols(); col++) {
				if (row == col) {
					pMatrix.set(row, col, (NonPrimeFieldElement) u.getOne());
				}
				else {
					pMatrix.set(row, col, (NonPrimeFieldElement) u.getZero());
				}
			}
		}
		
		Matrix<NonPrimeFieldElement> qMatrix = gMatrix.getMatrix(l, t1, 0, 0);
		
		Matrix<NonPrimeFieldElement> bZeroMatrix = qMatrix;
		for (int i = 0; i < n - t1; i++) {
			Matrix<NonPrimeFieldElement> zeroColumn = new Matrix<>(l, 1);
			for (int j = 0; j < zeroColumn.getRows(); j++) {
				zeroColumn.set(j, 0, (NonPrimeFieldElement) u.getZero());
			}
			bZeroMatrix = bZeroMatrix.appendColumn(zeroColumn);
		}
		
		Matrix<NonPrimeFieldElement> bMatrix = bZeroMatrix.mul(pMatrix);		
		Matrix<NonPrimeFieldElement> aMatrix = gMatrix.getMatrix(k, l, 0, 0);		
		Matrix<NonPrimeFieldElement> xMatrix = aMatrix.mul(bMatrix);
		
		Matrix<NonPrimeFieldElement> cCrMatrix = uMatrix.mul(gMatrix).add(xMatrix);
		
		Matrix<NonPrimeFieldElement> codeWord = new Matrix<>(1, n);
		codeWord.set(0, 0, new NonPrimeFieldElement(p, m, N, 0));
		codeWord.set(0, 1, new NonPrimeFieldElement(p, m, N, 1));
		codeWord.set(0, 2, new NonPrimeFieldElement(p, m, N, 2));
		codeWord.set(0, 3, new NonPrimeFieldElement(p, m, N, 3));
		
		Matrix<NonPrimeFieldElement> errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 0));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 9));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 1));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 8));
		
		Matrix<NonPrimeFieldElement> channelWord = codeWord.add(errorVector);
		Matrix<NonPrimeFieldElement> syndrome = channelWord.mul(hMatrix.transpose());
		Matrix<NonPrimeFieldElement> calculatedErrorVector = calculateErrorVector(p, m, N, q, n, k, d, syndrome, hMatrix);
		Matrix<NonPrimeFieldElement> decodedCodeWord = gMatrix.transpose().appendColumn(channelWord.sub(errorVector)).getMatrix(k, k + 1, 0, 0).standardize().getColumn(k).transpose();
		
		assertTrue(calculatedErrorVector.equals(errorVector));
	}
}
