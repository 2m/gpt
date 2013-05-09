package lt.doubleem.gpt;

import static lt.doubleem.gpt.ErrorVectors.getErrorVecors;
import static lt.doubleem.gpt.MrdEncodeTest.getGeneratorMatrix;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MrdDecodeTest {

	@Test
	public void shouldCalculateAllErrorVectors() {
		BigInteger p = BigInteger.valueOf(2);
		BigInteger m = BigInteger.valueOf(1);
		BigInteger N = BigInteger.valueOf(4);

		int n = 4; // code word length <= N
		int k = 2; // code dimension

		Matrix<NonPrimeFieldElement> gMatrix = getGeneratorMatrix(p, m, N, n, k);
		Matrix<NonPrimeFieldElement> hMatrix = gMatrix.dual();

		Matrix<NonPrimeFieldElement> codeWord = new Matrix<>(1, n);
		codeWord.set(0, 0, new NonPrimeFieldElement(p, m, N, 0));
		codeWord.set(0, 1, new NonPrimeFieldElement(p, m, N, 1));
		codeWord.set(0, 2, new NonPrimeFieldElement(p, m, N, 2));
		codeWord.set(0, 3, new NonPrimeFieldElement(p, m, N, 3));

		for (Matrix<NonPrimeFieldElement> e : getErrorVecors(p, m, N, n, k)) {
			Matrix<NonPrimeFieldElement> syndrome = codeWord.add(e).mul(hMatrix.transpose());
			if (!syndrome.isZero()) {
				assertEquals(e, calculateErrorVector(p, m, N, n, k, syndrome, hMatrix));
			}
		}
	}

	public static Matrix<NonPrimeFieldElement> calculateErrorVector(BigInteger p, BigInteger m, BigInteger N, int n, int k,
			Matrix<NonPrimeFieldElement> syndrome, Matrix<NonPrimeFieldElement> hMatrix) {
		
		
		BigInteger q = p.pow(m.intValue());
		
		int d = n - k + 1; // code distance

		LinearizedPolynomial<NonPrimeFieldElement> syndromePolynomial = new LinearizedPolynomial<>(q);
		for (int i = 0; i <= d - 2; i++) {
			syndromePolynomial.setCoef(i, syndrome.get(0, i));
		}

		LinearizedPolynomial<NonPrimeFieldElement> x_q_d_1 = new LinearizedPolynomial<>(q);
		x_q_d_1.setCoef(d - 1, new NonPrimeFieldElement(p, m, N, 0));

		LinearizedPolynomial<NonPrimeFieldElement> fi_1 = x_q_d_1;
		LinearizedPolynomial<NonPrimeFieldElement> fi = syndromePolynomial;

		LinearizedPolynomial<NonPrimeFieldElement> bi_1 = new LinearizedPolynomial<>(q);
		bi_1.setCoef(0, new NonPrimeFieldElement(p, m, N, 0));
		LinearizedPolynomial<NonPrimeFieldElement> bi_2 = new LinearizedPolynomial<>(q);
		LinearizedPolynomial<NonPrimeFieldElement> bi = null;

		LinearizedPolynomial<NonPrimeFieldElement> gi;

		int mi = 0;
		while (true) {

			if (fi_1.deg() >= (d - 1) / 2 && fi.deg() < (d - 1) / 2) {
				break;
			}

			mi = mi + 1;

			ArrayList<LinearizedPolynomial<NonPrimeFieldElement>> result = fi_1.divisionWithRemainder(fi);
			gi = result.get(0);

			fi_1 = fi;
			fi = result.get(1);

			bi = gi.mul(bi_1).add(bi_2);

			bi_2 = bi_1;
			bi_1 = bi;
		}

		if (bi == null) {
			throw new ArithmeticException("Unable to fivide fi_1 by fi");
		}

		NonPrimeFieldElement miu = (NonPrimeFieldElement) bi.getCoef(bi.deg()).inv();
		LinearizedPolynomial<NonPrimeFieldElement> e = bi.mul(miu);
		LinearizedPolynomial<NonPrimeFieldElement> f = fi.mul(miu).mul(BigInteger.valueOf(-1).pow(mi - 1).intValue());

		if (!f.equals(e.mul(syndromePolynomial).rem(x_q_d_1))) {
			throw new ArithmeticException("Calculated f and e polynomials do not satisfy main equation.");
		}

		List<NonPrimeFieldElement> eRoots = e.getRoots();

		if (eRoots.size() == 0) {
			throw new ArithmeticException("No roots found of polynomial e");
		}

		if (mi != eRoots.size()) {
			throw new ArithmeticException(String.format("mi is not the same as eRoots.size()! mi:%s, size:%s", mi, eRoots.size()));
		}

		Matrix<NonPrimeFieldElement> zLinearSystem = new Matrix<>(mi, mi + 1);
		for (int row = 0; row < mi; row++) {
			// TODO: fix pow for general case
			for (int col = 0; col < mi; col++) {
				zLinearSystem.set(row, col, (NonPrimeFieldElement) eRoots.get(col).pow(q.pow(row)));
			}
			zLinearSystem.set(row, mi, (NonPrimeFieldElement) syndrome.get(0, row).pow(q.pow(row)));
		}

		zLinearSystem = zLinearSystem.standardize();

		Matrix<NonPrimeFieldElement> z = zLinearSystem.getColumn(mi).transpose();
		Matrix<NonPrimeFieldElement> zMatrix = new Matrix<>(mi, d - 2 + 1);
		for (int row = 0; row < mi; row++) {
			// TODO: fix pow for general case
			for (int col = 0; col < d - 2 + 1; col++) {
				zMatrix.set(row, col, (NonPrimeFieldElement) z.get(0, row).pow(q.pow(col)));
			}
		}

		Matrix<NonPrimeFieldElement> yMatrix = zMatrix.mul(hMatrix.transpose().pseudoinv());

		Matrix<NonPrimeFieldElement> eMatrix = new Matrix<>(1, eRoots.size());
		for (int col = 0; col < eRoots.size(); col++) {
			eMatrix.set(0, col, eRoots.get(col));
		}
		Matrix<NonPrimeFieldElement> calculatedErrorVector = eMatrix.mul(yMatrix);

		return calculatedErrorVector;
	}
}
