package lt.doubleem.gpt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MrdDecodeTest {
	
	@Test
	public void shouldDecodeMrd() {
		BigInteger p = BigInteger.valueOf(2);
		BigInteger m = BigInteger.valueOf(4);
		BigInteger mod = p.pow(m.intValue());
		
		List<NonPrimeFieldElement> s = new ArrayList<>();
		s.add(new NonPrimeFieldElement(p, m, 2));
		s.add(new NonPrimeFieldElement(p, m, 5));
		s.add(new NonPrimeFieldElement(p, m, 7));
		
		LinearizedPolynomial<NonPrimeFieldElement> syndromePolynomial = new LinearizedPolynomial<>(mod);
		int d = 4;
		for (int i = 0; i <= d - 2; i++) {
			syndromePolynomial.setCoef(i, s.get(i));
		}
		
		LinearizedPolynomial<NonPrimeFieldElement> fi_1 = new LinearizedPolynomial<>(mod);
		fi_1.setCoef(d-1, new NonPrimeFieldElement(p, m, 0));		
		LinearizedPolynomial<NonPrimeFieldElement> fi = syndromePolynomial;
		
		LinearizedPolynomial<NonPrimeFieldElement> bi_1 = new LinearizedPolynomial<>(mod);
		bi_1.setCoef(1, new NonPrimeFieldElement(p, m, 0));
		LinearizedPolynomial<NonPrimeFieldElement> bi_2 = new LinearizedPolynomial<>(mod);
		LinearizedPolynomial<NonPrimeFieldElement> bi = null;
		
		LinearizedPolynomial<NonPrimeFieldElement> gi;
		
		int mi = 0;
		while (true) {
			mi = mi + 1;
			if (fi_1.deg() >= (d - 1) / 2 && fi.deg() < (d - 1) / 2) {
				break;
			}
			
			ArrayList<LinearizedPolynomial<NonPrimeFieldElement>> result = fi_1.divisionWithRemainder(fi);
			gi = result.get(0);
			
			fi_1 = fi;
			fi = result.get(1);
			
			bi = gi.mul(bi_1).add(bi_2);
			
			bi_2 = bi_1;
			bi_1 = bi;
		}
		
		NonPrimeFieldElement miu = (NonPrimeFieldElement) bi.getCoef(mi).inv();
		LinearizedPolynomial<NonPrimeFieldElement> e = bi.mul(miu);
		LinearizedPolynomial<NonPrimeFieldElement> f = fi.mul(miu).mul(BigInteger.valueOf(-1).pow(mi-1).intValue());
		List<NonPrimeFieldElement> eRoots = e.getRoots();
		
		if (eRoots.size() == 0) {
			// temporary workaround to test arithmetic operations
			for (int r = 0; r < mi; r++) {
				eRoots.add(s.get(r));
			}
		}
		
		Matrix<NonPrimeFieldElement> zLinearSystem = new Matrix<>(mi, mi + 1);
		for (int row = 0; row < mi; row++) {
			for (int col = 0; col < mi; col++) {
				zLinearSystem.set(row, col, (NonPrimeFieldElement) eRoots.get(col).inv().pow(mod.pow(row)));
			}
			zLinearSystem.set(row, mi, (NonPrimeFieldElement) s.get(row).inv().pow(mod.pow(row)));
		}
		
		System.out.println(zLinearSystem);		
		zLinearSystem.standardize();
		System.out.println(zLinearSystem);
		
		List<NonPrimeFieldElement> z = zLinearSystem.getColumn(mi);
		Matrix<NonPrimeFieldElement> zMatrix = new Matrix<>(mi, d - 2 + 1);
		for (int row = 0; row < mi; row++) {
			for (int col = 0; col < d - 2 + 1; col++) {
				zMatrix.set(row, col, (NonPrimeFieldElement) z.get(row).pow(p.pow(m.intValue()).pow(col)));
			}
		}
		System.out.println(zMatrix);
		
		List<FieldElement> hList = new ArrayList<>();
		hList.add(new NonPrimeFieldElement(p, m, BigInteger.valueOf(0)));
		hList.add(new NonPrimeFieldElement(p, m, BigInteger.valueOf(1)));
		hList.add(new NonPrimeFieldElement(p, m, BigInteger.valueOf(2)));
		hList.add(new NonPrimeFieldElement(p, m, BigInteger.valueOf(3)));
		
		Matrix<NonPrimeFieldElement> hMatrix = new Matrix<>(d - 2 + 1, m.intValue());
		for (int row = 0; row < d - 2 + 1; row++) {
			for (int col = 0; col < m.intValue(); col++) {
				hMatrix.set(row, col, (NonPrimeFieldElement) hList.get(col).pow(p.pow(m.intValue()).pow(row)));
			}
		}		
		System.out.println(hMatrix);
		
		Matrix<NonPrimeFieldElement> yMatrix = zMatrix.mul(hMatrix.inv());
		System.out.println(yMatrix);
		
		Matrix<NonPrimeFieldElement> eMatrix = new Matrix<>(1, eRoots.size());
		for (int col = 0; col < eRoots.size(); col++) {
			eMatrix.set(0, col, eRoots.get(col));
		}
		Matrix<NonPrimeFieldElement> errMatrix = eMatrix.mul(yMatrix);
		
		System.out.println(errMatrix);
	}
}
