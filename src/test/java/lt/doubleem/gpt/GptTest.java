package lt.doubleem.gpt;

import static lt.doubleem.gpt.ErrorVectors.getAnyErrorVector;
import static lt.doubleem.gpt.Messages.getAnyMessage;
import static lt.doubleem.gpt.MrdDecodeTest.calculateErrorVector;
import static lt.doubleem.gpt.MrdEncodeTest.getGeneratorMatrix;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

public class GptTest {
	
	@Test
	public void shouldCreatePrivateKey() {
		BigInteger p = BigInteger.valueOf(2);
		BigInteger m = BigInteger.valueOf(1);
		BigInteger N = BigInteger.valueOf(4);
		
		int n = 4; // code word length <= N
		int k = 2; // code dimension
		
		Matrix<NonPrimeFieldElement> gMatrix = getGeneratorMatrix(p, m, N, n, k);
		Matrix<NonPrimeFieldElement> hMatrix = gMatrix.dual();
		
		Matrix<NonPrimeFieldElement> publicKey = getPublicKey(p, m, N, n, k, gMatrix);
		
		Messages.r = new Random(123);
		ErrorVectors.r = new Random(123);
		
		for (int i = 0; i < 1000; i++) {
			Matrix<NonPrimeFieldElement> message = getAnyMessage(p, m, N, k);		
			Matrix<NonPrimeFieldElement> errorVector = getAnyErrorVector(p, m, N, n, k);
			
			Matrix<NonPrimeFieldElement> channelWord = message.mul(gMatrix).add(errorVector);
			Matrix<NonPrimeFieldElement> syndrome = channelWord.mul(hMatrix.transpose());
			Matrix<NonPrimeFieldElement> calculatedErrorVector = calculateErrorVector(p, m, N, n, k, syndrome, hMatrix);
			Matrix<NonPrimeFieldElement> decodedMessage = gMatrix.transpose().appendColumn(channelWord.sub(calculatedErrorVector)).getMatrix(k, k + 1, 0, 0).standardize().getColumn(k).transpose();
			
			assertEquals(errorVector, calculatedErrorVector);
			assertEquals(message, decodedMessage);
		}
	}
	
	public static Matrix<NonPrimeFieldElement> getPublicKey(BigInteger p, BigInteger m, BigInteger N, int n, int k, Matrix<NonPrimeFieldElement> gMatrix) {
		BigInteger q = p.pow(m.intValue());
		
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
		
		return uMatrix.mul(gMatrix).add(xMatrix);
	}
}
